package com.melbourne.parking


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.snackbar.Snackbar
import com.melbourne.parking.databinding.ActivityMainBinding
import com.melbourne.parking.ui.FilterDialogFragment
import com.melbourne.parking.ui.ParkingDetailFragment
import com.melbourne.parking.ui.ParkingFragment


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            showMapFragment()
        }



    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.menu_filter -> {
                FilterDialogFragment().show(supportFragmentManager, "Filter Dialog")
                true
            }
            R.id.action_settings -> {
                showMapFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun showMapFragment() {
;

        val fragment = MapsFragment()
        // Open fragment

        getSupportFragmentManager()
            .beginTransaction().replace(R.id.nav_host_fragment_content_main,fragment)
            .commit();


//        mapFragment?.getMapAsync { googleMap ->
//            // Ensure all places are visible in the map
//            googleMap.setOnMapLoadedCallback {
//                val bounds = LatLngBounds.builder()
//                places.forEach { bounds.include(it.latLng) }
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
//            }
//
//            //addMarkers(googleMap)
//            addClusteredMarkers(googleMap)
//
//            // Set custom info window adapter
//            // googleMap.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))
//        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}






