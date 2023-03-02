package com.melbourne.parking.ui

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.melbourne.parking.R

class MapsFragment : Fragment(), OnMapReadyCallback  {

    private lateinit var googleMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val melbourne = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(melbourne).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(melbourne))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        // TODO: Add markers for parking locations

        val melbourne = LatLng(-34.0, 151.0)

        googleMap.addMarker(MarkerOptions().position(melbourne).title("Marker in Sydney"))
    }


}