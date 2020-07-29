package com.colinmobile.umrah

import android.app.Application
import android.content.Context
import android.os.Build
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.colinmobile.umrah.AppConfig.TAG
import org.intellij.lang.annotations.Language
import java.util.*

open class AppController : Application() {
    private var mRequestQueue: RequestQueue? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val requestQueue: RequestQueue?
        get() {
            if (mRequestQueue == null) {
                mRequestQueue =
                    Volley.newRequestQueue(applicationContext)
            }
            return mRequestQueue
        }

    fun <T> addToRequestQueue(req: Request<T>, tag: String?) {
        req.setTag(if (TextUtils.isEmpty(tag)) TAG else tag)
        requestQueue!!.add<T>(req)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.setTag(TAG)
        requestQueue!!.add<T>(req)
    }

    fun cancelPendingRequests(tag: Any?) {
        if (mRequestQueue != null) {
            mRequestQueue!!.cancelAll(tag)
        }
    }

    companion object {
        @get:Synchronized
        var instance: AppController? = null
            private set
    }

//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(LocalizationUtil.applyLanguage(base, "str"))
//    }

//    object LocalizationUtil{
//        fun applyLanguage(context: Context, language: String):Context{
//            val locale = Locale(language)
//            val configuration = context.resources.configuration
//            val displayMetrics = context.resources.displayMetrics
//            Locale.setDefault(locale)
//            return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
//                configuration.setLocale(locale)
//                context.createConfigurationContext(configuration)
//            }else{
//                configuration.locale = locale
//                context.resources.updateConfiguration(configuration, displayMetrics)
//                context
//            }
//        }
//    }
}