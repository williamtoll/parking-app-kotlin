package com.melbourne.parking.repositories;

import android.util.Log
import com.melbourne.parking.model.ParkingMeter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class ParkingRepository {
    private val TAG = "ParkingRepository"

    suspend fun fetchParkingMeters(streetName: String, tapAndGo: String, creditCard: String ): List<ParkingMeter> {
        var req =
            "https://data.melbourne.vic.gov.au/api/records/1.0/search/?dataset=on-street-car-parking-meters-with-location&q=&facet=creditcard&facet=tapandgo&facet=streetname"

        if(streetName.isNotEmpty())
        {
            req="${req}&q=${streetName}"
        }

        if(!tapAndGo.isNullOrBlank()){
            req="${req}&refine.tapandgo=${tapAndGo}"
        }
        if(!creditCard.isNullOrBlank()){
            req="${req}&refine.creditcard=${creditCard}"
        }

        Log.d(TAG, "fetchParkingMeters: ${req}")

        val request = Request.Builder()
            .url(req)
            .build()

        val client = OkHttpClient()

        return withContext(Dispatchers.IO) {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }

                val body = response.body?.string() ?: throw IOException("No response body")

                val jsonArray = JSONObject(body).getJSONArray("records")
                val meters = mutableListOf<ParkingMeter>()

                for (i in 0 until jsonArray.length()) {
                    val jsonMeter = jsonArray.getJSONObject(i)
                    val jsonFields = jsonMeter.getJSONObject("fields")
                    val meter = ParkingMeter(
                        id = jsonMeter.getString("recordid"),
                        streetName = jsonFields.getString("streetname"),
                        lat = jsonFields.getJSONArray("location").getDouble(0),
                        lng = jsonFields.getJSONArray("location").getDouble(1),
                        hasCreditCard = jsonFields.getString("creditcard") == "YES",
                        hasTapAndGo = jsonFields.getString("tapandgo") == "YES"
                    )



                    meters.add(meter)
                }
                Log.d(TAG, "fetchParkingMeters: $meters")

                meters
            }
        }
    }

}
