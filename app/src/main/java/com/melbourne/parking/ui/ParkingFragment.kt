package com.melbourne.parking.ui

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melbourne.parking.R
import com.melbourne.parking.model.ParkingMeter
import com.melbourne.parking.repositories.ParkingRepository
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class ParkingFragment : Fragment(), ParkingRecyclerViewAdapter.OnItemClickListener {

    private val TAG = "ParkingFragment"
    private var columnCount = 1

    var mAdapter: ParkingRecyclerViewAdapter? = null
    var mList = ArrayList<ParkingMeter>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchView: EditText
    private lateinit var emptyView: TextView
    private lateinit var viewModel: ParkingViewModel
//    private lateinit var adapter: ParkingRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onItemClick(item: ParkingMeter) {

        Log.d(TAG, "onItemClick: ${item.id} ${item.streetName}")
        // Handle click event on a list item
        val detailFragment = ParkingDetailFragment.newInstance(item)
        requireFragmentManager().beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ParkingViewModel().fetchParkingList("","","")
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        mAdapter = ParkingRecyclerViewAdapter(mList, this) // pass listener to adapter constructor

        recyclerView = view.findViewById(R.id.parkingRecyclerView)
        progressBar = view.findViewById(R.id.parkingProgressBar)
        emptyView = view.findViewById(R.id.emptyView)
        searchView = view.findViewById(R.id.searchView)

//        // Get the view model for this fragment
        viewModel = ViewModelProvider(this).get(ParkingViewModel::class.java)
//
//        // Observe changes in the list of parking meters
        viewModel.parkingList.observe(viewLifecycleOwner, { parkingList ->
//            mAdapter.submitList(parkingList)
            Log.d(TAG, "viewmodel change ${parkingList}")
            mList.clear()
            mList.addAll(parkingList)
            mAdapter?.notifyDataSetChanged()
            updateEmptyViewVisibility(parkingList)
        })



        // Set up search functionality
        searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Filter parking list based on search query
                filterBy(s.toString(),"","")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }
        })


        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }



    fun filterBy(streetName: String, tapAndGo: String, creditCard: String) {
        lifecycleScope.launch {
            try {
                val filteredList = ParkingRepository().fetchParkingMeters(streetName,tapAndGo,creditCard)
                Log.d(TAG, "filterBy: creditcard ${creditCard} ")

                Log.d(TAG, "filterBy: filteredlist ${filteredList} ")

                if(filteredList.isNotEmpty()) {
                    mList.clear()
                    mList.addAll(filteredList)
                    mAdapter?.notifyDataSetChanged()
                }else{
                    emptyView.visibility = View.VISIBLE
                }

            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch parking meters", e)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_filter -> {
                showFilterDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun showFilterDialog() {
        val dialog = FilterDialogFragment()
        dialog.show(childFragmentManager, "FilterDialogFragment")
    }

    private fun updateEmptyViewVisibility(parkingList: List<ParkingMeter>) {
        if (parkingList.isEmpty()) {
            emptyView.visibility = View.VISIBLE
        } else {
            emptyView.visibility = View.GONE
        }
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            ParkingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

}