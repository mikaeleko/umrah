package com.colinmobile.umrah

object AppConfig {
    const val TAG = "gplaces"
    const val RESULTS = "results"
    const val STATUS = "status"
    const val OK = "OK"
    const val ZERO_RESULTS = "ZERO_RESULTS"
    const val REQUEST_DENIED = "REQUEST_DENIED"
    const val OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT"
    const val UNKNOWN_ERROR = "UNKNOWN_ERROR"
    const val INVALID_REQUEST = "INVALID_REQUEST"

    //    Key for nearby places json from google
    const val GEOMETRY = "geometry"
    const val LOCATION = "location"
    const val LATITUDE = "lat"
    const val LONGITUDE = "lng"
    const val ICON = "icon"
    const val SUPERMARKET_ID = "id"
    const val NAME = "name"
    const val PLACE_ID = "place_id"
    const val REFERENCE = "reference"
    const val VICINITY = "vicinity"
    const val PLACE_NAME = "place_name"

    // remember to change the browser api key
    const val GOOGLE_BROWSER_API_KEY = "AIzaSyBJvlD3dqnz42r9obhEClc2dEJAdXt9IK8"
    const val PLAY_SERVICES_RESOLUTION_REQUEST = 9000
    const val PROXIMITY_RADIUS = 5000

    // The minimum distance to change Updates in meters
    const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

    // The minimum time between updates in milliseconds
    const val MIN_TIME_BW_UPDATES = 1000 * 60 * 1 // 1 minute
        .toLong()
}