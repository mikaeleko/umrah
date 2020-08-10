package com.colinmobile.umrah

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity() {
    var default = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if(default == 0){
            changeLocale(this, "id")
//            default++
//        }
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher)

        val constraintSalat = findViewById(R.id.constraint_salat_schedule)as ConstraintLayout
        constraintSalat.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, SalatActivity::class.java)
            i.putExtra("subuh",subuhTime)
            i.putExtra("dzuhur",dzuhurTime)
            i.putExtra("asar",asarTime)
            i.putExtra("magrib",magribTime)
            i.putExtra("isya",isyaTime)
            startActivity(i)
        })

        val constraintQibla = findViewById(R.id.constraint_qibla)as ConstraintLayout
        constraintQibla.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, CompasActivity::class.java)
            startActivity(intent)
        })

        val constraintMosque = findViewById(R.id.constraint_mosque)as ConstraintLayout
        constraintMosque.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, NearbyActivity::class.java)
            i.putExtra("type","mosque")
            startActivity(i)
        })

        val constraintPrayer = findViewById(R.id.constraint_prayer_menu)as ConstraintLayout
        constraintPrayer.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, PrayerActivity::class.java)
            startActivity(i)
        })

        val constraintLive = findViewById(R.id.constraint_live_guide)as ConstraintLayout
        constraintLive.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, GuideActivity::class.java)
            startActivity(i)
        })

        val constraintRestaurant = findViewById(R.id.constraint_restaurant)as ConstraintLayout
        constraintRestaurant.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, NearbyActivity::class.java)
            i.putExtra("type","restaurant")
            startActivity(i)
        })

        val constraintAtm = findViewById(R.id.constraint_atm)as ConstraintLayout
        constraintAtm.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, NearbyActivity::class.java)
            i.putExtra("type","atm")
            startActivity(i)
        })

        val constraintShop = findViewById(R.id.constraint_shop)as ConstraintLayout
        constraintShop.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, NearbyActivity::class.java)
            i.putExtra("type","shop")
            startActivity(i)
        })

        val constraintHospital = findViewById(R.id.constraint_hospital)as ConstraintLayout
        constraintHospital.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, NearbyActivity::class.java)
            i.putExtra("type","hospital")
            startActivity(i)
        })

        val constraintPharmacy = findViewById(R.id.constraint_pharmacy)as ConstraintLayout
        constraintPharmacy.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, NearbyActivity::class.java)
            i.putExtra("type","pharmacy")
            startActivity(i)
        })

        val constraintMoneyExchange = findViewById(R.id.constraint_money_exchange)as ConstraintLayout
        constraintMoneyExchange.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, NearbyActivity::class.java)
            i.putExtra("type","money exchange")
            startActivity(i)
        })

        val constraintCarRent = findViewById(R.id.constraint_car_rent)as ConstraintLayout
        constraintCarRent.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, NearbyActivity::class.java)
            i.putExtra("type","car rent")
            startActivity(i)
        })

        val constraintBusStation = findViewById(R.id.constraint_bus_station)as ConstraintLayout
        constraintBusStation.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, NearbyActivity::class.java)
            i.putExtra("type","bus station")
            startActivity(i)
        })

        getSalatTime()
    }

    fun getSalatTime(){
        val queue = Volley.newRequestQueue(this)
        val url = "http://api.aladhan.com/v1/calendarByCity?city=Mecca&country=Saudi%20Arabia&method=2&year=2020"
        val dialog = setProgressDialog(this, "Loading..")
        dialog.show()
        val request = JsonObjectRequest(Request.Method.GET, url.toString(),null, Response.Listener{
                response ->
            try {
                if(response!=null){
                    try {
                        if (response.getString("status").equals("OK", ignoreCase = true)) {
                            val data = response.getJSONArray("data")
                            for (i in 0 until data.length()) {
                                val timings = data.getJSONObject(i)
                                subuhTime = timings.getJSONObject("timings").getString("Sunrise").toString().substring(0, 5)
                                dzuhurTime = timings.getJSONObject("timings").getString("Dhuhr").toString().substring(0, 5)
                                asarTime = timings.getJSONObject("timings").getString("Asr").toString().substring(0, 5)
                                magribTime = timings.getJSONObject("timings").getString("Maghrib").toString().substring(0, 5)
                                isyaTime = timings.getJSONObject("timings").getString("Isha").toString().substring(0, 5)
                                dialog.dismiss()
                            }
                        } else if (response.getString("status").equals("ZERO_RESULTS", ignoreCase = true)) {
                            Log.i("JSON response 2b", response.getString("status"))
                            dialog.dismiss()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        dialog.dismiss()
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                dialog.dismiss()
            }
        }, Response.ErrorListener { error ->
            if(error!=null){
                error.printStackTrace()
                dialog.dismiss()
            }
        })
        request.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int {
                return 50000
            }

            override fun getCurrentRetryCount(): Int {
                return 50000
            }

            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {
            }
        }
        queue?.add(request)

        setAlarm(subuhTime, resources.getString(R.string.subuh_title),resources.getString(R.string.subuh_message))
        setAlarm(dzuhurTime, resources.getString(R.string.dzuhur_title),resources.getString(R.string.dzuhur_message))
        setAlarm(asarTime, resources.getString(R.string.asar_title),resources.getString(R.string.asar_message))
        setAlarm(magribTime, resources.getString(R.string.magrib_title),resources.getString(R.string.magrib_message))
        setAlarm(isyaTime, resources.getString(R.string.isya_title),resources.getString(R.string.isya_message))
    }

    fun setAlarm(salatTime:String, notifTitle:String, notifMessage:String) {
//        val alertTime: Long = GregorianCalendar().getTimeInMillis() + 7 * 1000
        var requestCode = (0..1000).random()
        val todayDate = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy/MM/dd")
        val todayString = formatter.format(todayDate)

        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
        val date: Date = sdf.parse(todayString+" "+salatTime)
        val alertTime = date.time
        Log.i("salatTime", "ReqCode : "+requestCode)
        Log.i("salatTime", "Time : "+alertTime)

        val alertIntent = Intent(applicationContext, SalatReceiver::class.java)
        alertIntent.putExtra("requestCode", requestCode)
        alertIntent.putExtra("title", notifTitle)
        alertIntent.putExtra("message", notifMessage)

        val alarmManager =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, alertTime] = PendingIntent.getBroadcast(
            applicationContext, requestCode, alertIntent,
            PendingIntent.FLAG_ONE_SHOT
        )
    }

    fun setProgressDialog(context: Context, message:String): AlertDialog {
        val llPadding = 30
        val ll = LinearLayout(context)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(context)
        tvText.text = message
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.textSize = 20.toFloat()
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setView(ll)

        val dialog = builder.create()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window?.attributes = layoutParams
        }
        return dialog
    }

    open fun changeLocale(context: Context, locale: String?) {
        val res: Resources = context.getResources()
        val conf: Configuration = res.getConfiguration()
        conf.locale = Locale(locale)
        res.updateConfiguration(conf, res.getDisplayMetrics())
    }

    private fun canResolveMobileLiveIntent(context: Context): Boolean {
        val intent = Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM")
            .setPackage("com.google.android.youtube")
        val pm: PackageManager = context.getPackageManager()
        val resolveInfo: List<*>? =
            pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo != null && !resolveInfo.isEmpty()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Membaca file menu dan menambahkan isinya ke action bar jika ada.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_information -> {
            val i = Intent(this@MainActivity, InformationActivity::class.java)
//            i.putExtra("type","pharmacy")
            startActivity(i)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun validateMobileLiveIntent(context: Context) {
        if (canResolveMobileLiveIntent(context)) {
            // Launch the live stream Activity
            startMobileLive(context)
        } else {
            // Prompt user to install or upgrade the YouTube app
        }
    }

    private fun createMobileLiveIntent(
        context: Context,
        description: String
    ): Intent {
        val intent = Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM")
            .setPackage("com.google.android.youtube")
        val referrer: Uri = Uri.Builder()
            .scheme("android-app")
            .appendPath(context.packageName)
            .build()
        intent.putExtra(Intent.EXTRA_REFERRER, referrer)
        if (!TextUtils.isEmpty(description)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, description)
        }
        return intent
    }

    private fun startMobileLive(context: Context) {
        val mobileLiveIntent = createMobileLiveIntent(context, "Streaming via ...")
        startActivity(mobileLiveIntent)
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }

}
