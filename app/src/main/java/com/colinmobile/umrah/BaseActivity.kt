package com.colinmobile.umrah

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*


abstract class BaseActivity : AppCompatActivity() {
    var subuhTime = "00:00"
    var dzuhurTime = "00:00"
    var asarTime = "00:00"
    var magribTime = "00:00"
    var isyaTime = "00:00"
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
//        val res: Resources = resources
//        // Change locale settings in the app.
//        val dm: DisplayMetrics = res.getDisplayMetrics()
//        val conf: Configuration = res.getConfiguration()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            conf.setLocale(Locale("id"))
//        } // API 17+ only.
//        // Use conf.locale = new Locale(...) if targeting lower versions
//        res.updateConfiguration(conf, dm)

//        val locale = Locale("id")
//        Locale.setDefault(locale)
//        val config = Configuration()
//        config.locale = locale
//        applicationContext.resources
//            .updateConfiguration(config, applicationContext.resources.displayMetrics)
        super.onCreate(savedInstanceState)
    }

//    open fun changeLocale(context: Context, locale: String?) {
//        val res: Resources = context.getResources()
//        val conf: Configuration = res.getConfiguration()
//        conf.locale = Locale(locale)
//        res.updateConfiguration(conf, res.getDisplayMetrics())
//    }

//    open fun changeDefault(newDefault: Int){
//        default = newDefault
//    }


}