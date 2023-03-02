package com.melbourne.parking.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.melbourne.parking.R
import com.melbourne.parking.model.ParkingMeter

class ParkingDetailFragment : Fragment() {
    private var parkingMeter: ParkingMeter? = null

    companion object {
        private const val ARG_PARKING_METER = "parkingMeter"

        fun newInstance(parkingMeter: ParkingMeter): ParkingDetailFragment {
            val args = Bundle().apply {
                putParcelable(ARG_PARKING_METER, parkingMeter)
            }
            return ParkingDetailFragment().apply {
                arguments = args
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        parkingMeter = arguments?.getParcelable(ARG_PARKING_METER)!!
        val view=inflater.inflate(R.layout.parking_detail_fragment, container, false)
        view.findViewById<TextView>(R.id.streetName).text = parkingMeter?.streetName
        view.findViewById<TextView>(R.id.creditCard).text = if(parkingMeter!!.hasCreditCard) "Credit Card Payment Available" else "NO CC Payment"
        return view
    }

}