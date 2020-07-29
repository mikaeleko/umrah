package com.colinmobile.umrah

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONException
import org.json.JSONObject

fun Context.NearbyIntent(): Intent {
    return Intent(this, NearbyActivity::class.java).apply {
//        putExtra(INTENT_USER_ID, user.id)
        Log.i("test","ok")
    }
}
class NearbyActivity : AppCompatActivity(), OnMapReadyCallback {
//    var mMap: GoogleMap? = null
    var requestQueue: RequestQueue? = null
    var type = "type"
    val GOOGLE_BROWSER_API_KEY = "AIzaSyDgxO2WybLD3Yih7Vz_77S2F0IFclPMnB4"
    val PROXIMITY_RADIUS = 3000
    //latlong-alharam
    val latitude = 24.467609//21.430777
    val longitude = 39.604179//39.856156
    val TAG = "gplaces"
    val RESULTS = "results"
    val STATUS = "status"
    val OK = "OK"
    val ZERO_RESULTS = "ZERO_RESULTS"
    val REQUEST_DENIED = "REQUEST_DENIED"
    val OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT"
    val UNKNOWN_ERROR = "UNKNOWN_ERROR"
    val INVALID_REQUEST = "INVALID_REQUEST"
    val GEOMETRY = "geometry"
    val LOCATION = "location"
    val LATITUDE = "lat"
    val LONGITUDE = "lng"
    val ICON = "icon"
    val SUPERMARKET_ID = "id"
    val NAME = "name"
    val PLACE_ID = "place_id"
    val REFERENCE = "reference"
    val VICINITY = "vicinity"
    val PLACE_NAME = "place_name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby)

        val mapFragment : SupportMapFragment? = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        requestQueue = Volley.newRequestQueue(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        with(googleMap) {
            addMarker(
                MarkerOptions()
                .position(LatLng(latitude,longitude))//mecca
                .title("Marker"))
            moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude,longitude)));
            animateCamera(CameraUpdateFactory.zoomTo(15.0F));
        }
        var bundle :Bundle ?=intent.extras
        type = intent.getStringExtra("type")
        title = type.capitalize()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        val type = "restaurant"
        val googlePlacesUrl =
            StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        googlePlacesUrl.append("location=").append(latitude).append(",").append(longitude)
        googlePlacesUrl.append("&radius=").append(PROXIMITY_RADIUS)
        googlePlacesUrl.append("&types=").append(type)
        googlePlacesUrl.append("&sensor=true")
        googlePlacesUrl.append("&key=$GOOGLE_BROWSER_API_KEY")

        val request = JsonObjectRequest(Request.Method.GET, googlePlacesUrl.toString(),null, Response.Listener{
                response ->
            try {
                if(response!=null){
                    parseLocationResult(response, googleMap);
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener {error ->
            if(error!=null){
                Log.e(TAG, "onErrorResponse: Error= " + error);
                Log.e(TAG, "onErrorResponse: Error= " + error.message);
            }
        })

        requestQueue?.add(request)
    }

    private fun parseLocationResult(result: JSONObject, mMap: GoogleMap) {
        var id: String
        var place_id: String
        var placeName: String? = null
        var reference: String
        var icon: String
        var vicinity: String? = null
        var latitude: Double
        var longitude: Double
        try {
            val jsonArray = result.getJSONArray("results")
            if (result.getString(STATUS).equals(OK, ignoreCase = true)) {
                mMap?.clear()
                for (i in 0 until jsonArray.length()) {
                    val place = jsonArray.getJSONObject(i)
                    id = place.getString(SUPERMARKET_ID)
                    place_id = place.getString(PLACE_ID)
                    if (!place.isNull(NAME)) {
                        placeName = place.getString(NAME)
                    }
                    if (!place.isNull(VICINITY)) {
                        vicinity = place.getString(VICINITY)
                    }
                    latitude =
                        place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LATITUDE)
                    longitude =
                        place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LONGITUDE)
                    reference = place.getString(REFERENCE)
                    icon = place.getString(ICON)
                    val markerOptions = MarkerOptions()
                    val latLng = LatLng(latitude, longitude)
                    markerOptions.position(latLng)
                    markerOptions.title("$placeName : $vicinity")
                    mMap?.addMarker(markerOptions)
                }
                Toast.makeText(
                    baseContext, jsonArray.length().toString() + " "+type,
                    Toast.LENGTH_LONG
                ).show()
            } else if (result.getString(STATUS).equals(ZERO_RESULTS, ignoreCase = true)) {
                Toast.makeText(
                    baseContext, "Sorry Not Found",
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e(TAG, "parseLocationResult: Error=" + e.message)
        }
    }

}
