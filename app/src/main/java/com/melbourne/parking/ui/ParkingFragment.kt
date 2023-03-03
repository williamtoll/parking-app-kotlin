package com.melbourne.parking.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melbourne.parking.R
import com.melbourne.parking.model.ParkingMeter

/**
 * A fragment representing a list of Items.
 */
class ParkingFragment : Fragment(), ParkingRecyclerViewAdapter.OnItemClickListener,
    FilterDialogFragment.FilterDialogListener {

    private val TAG = "ParkingFragment"
    private var columnCount = 1

    var mAdapter: ParkingRecyclerViewAdapter? = null
    var mList = ArrayList<ParkingMeter>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchView: EditText
    private lateinit var emptyView: TextView
    private lateinit var viewModel: ParkingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }


    override fun onFilterSelected(all: Boolean, tapAndGo: Boolean) {
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
        setHasOptionsMenu(true)

        var viewModel: ParkingViewModel =
            ViewModelProvider(this).get(ParkingViewModel::class.java)
        viewModel.fetchParkingList("", "")
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

        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProvider(this).get(ParkingViewModel::class.java)

        viewModel.parkingList.observe(viewLifecycleOwner, { parkingList ->
            mList.clear()
            mList.addAll(parkingList)
            mAdapter?.notifyDataSetChanged()
            updateEmptyViewVisibility(parkingList)
        })

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_parking, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        // Set up the search view
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Handle search query submission
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Handle search query text change
                viewModel.fetchParkingList(newText, "")
                return true
            }
        })
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

        dialog.setListener(object : FilterDialogFragment.FilterDialogListener {

            override fun onFilterSelected(all: Boolean, tapAndGo: Boolean) {
                if (all) {
                    viewModel.fetchParkingList("", "")
                } else {
                    // For demonstration purpose of the filtering feature. We retrieve the list of parking that doesnt have creditcard and tap and go available. With this filter we can show a different list in the recycler view
                    viewModel.fetchParkingList("", "NO")
                }

            }
        })


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