package com.colinmobile.umrah

import android.R
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.Intent.FILL_IN_ACTION
import android.content.IntentFilter
import android.net.sip.*
import android.os.IBinder
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import java.text.ParseException


class IncomingCallService : Service() {
    val notifyID = 9001
    var callReceiver: IncomingCallReceiver = IncomingCallReceiver()
    var mSipProfile: SipProfile? = null
    var builder: SipProfile.Builder? = null
    var call: SipAudioCall? = null
    var mSipManager: SipManager? = null
    var sipManager2: SipManager? = null
    var username: String? = null
    var password: String? = null
    var domain: String? = null
    var Status: String? = null
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
    fun load() {
        val ctx: Context = applicationContext
//        val sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
//        username = sharedPreferences.getString("username1", "Error")
//        password = sharedPreferences.getString("password1", "Error")
//        domain = sharedPreferences.getString("domain1", "Error")

        val prefs =
            PreferenceManager.getDefaultSharedPreferences(baseContext)
        username = prefs.getString("namePref", "")
//        val domain = prefs.getString("domainPref", "")
        password = prefs.getString("passPref", "")
        domain = resources.getString(com.colinmobile.umrah.R.string.SERVER_URL)
    }
    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter()
        filter.addAction("android.SipDemo.INCOMING_CALL")
        callReceiver = IncomingCallReceiver()
        this.registerReceiver(callReceiver, filter)
        if (mSipManager == null) {
            mSipManager = SipManager.newInstance(applicationContext)
        }
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        load()
        Log.d("Username", username)
        Log.d("Password", password)
        Log.d("Domain", domain)

        Toast.makeText(applicationContext, "Service Started", Toast.LENGTH_SHORT).show()
        try {
            builder = SipProfile.Builder(username+"@"+domain, domain)
            builder!!.setPassword(password)
//            builder!!.setPort(5060)
//            builder!!.setOutboundProxy("10.0.2.51")
            builder!!.setProtocol("UDP")
            builder!!.setAutoRegistration(true)
            builder!!.setSendKeepAlive(true)
            mSipProfile = builder!!.build()
            val intent1 = Intent()
            intent1.action = "android.SipDemo.INCOMING_CALL"
            val pi = PendingIntent.getBroadcast(applicationContext, 0, intent1, FILL_IN_ACTION)
            mSipManager!!.open(mSipProfile, pi, null)
            Toast.makeText(application, "listener Started", Toast.LENGTH_SHORT).show()
            mSipManager!!.setRegistrationListener(
                mSipProfile?.getUriString(),
                object : SipRegistrationListener {
                    override fun onRegistering(localProfileUri: String) {
                        Log.d("Trying", "Register")
                    }

                    override fun onRegistrationDone(localProfileUri: String, expiryTime: Long) {
                        Status = "done"
                        sendNotification()
                        sipManager2 = mSipManager
                        Log.d("Manager", mSipManager.toString())
                        Log.d("Success", "complete")
                    }

                    override fun onRegistrationFailed(
                        localProfileUri: String,
                        errorCode: Int,
                        errorMessage: String
                    ) {
                        Log.d("Failed", Integer.toString(errorCode))
                        sendNotification1()
                    }
                })
        } catch (e: SipException) {
            e.printStackTrace()
            Toast.makeText(application, "SipListener failed", Toast.LENGTH_SHORT).show()
//            Log.d("reg", "error")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun onDestory() {
        super.onDestroy()
        sipManager2 = null
        if (call != null) {
            call!!.close()
        }
        closeLocalProfile()
        if (callReceiver != null) {
            unregisterReceiver(callReceiver)
        }
    }

    fun closeLocalProfile() {
        if (mSipManager == null) {
            return
        }
        try {
            if (mSipProfile != null) {
                mSipManager!!.close(mSipProfile!!.uriString)
            }
        } catch (ee: Exception) {
//            Log.d("Sip Profile Error", ee.toString())
        }
    }
    private fun sendNotification() {
        val mNotifyBuilder: NotificationCompat.Builder
        val mNotificationManager: NotificationManager
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotifyBuilder = NotificationCompat.Builder(this)
            .setContentTitle("Account is Ready")
            .setContentText("Registration Success")
//            .setSmallIcon(R.drawable.success)
        mNotifyBuilder.setContentText("Ready")
        mNotificationManager.notify(notifyID, mNotifyBuilder.build())
    }

    private fun sendNotification1() {
        val mNotifyBuilder: NotificationCompat.Builder
        val mNotificationManager: NotificationManager
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotifyBuilder = NotificationCompat.Builder(this)
            .setContentTitle("Account is Not Ready")
            .setContentText("Registration Failed")
//            .setSmallIcon(R.drawable.failed)
        mNotifyBuilder.setContentText("Failed")
        mNotifyBuilder.setAutoCancel(true)
        mNotificationManager.notify(notifyID, mNotifyBuilder.build())
    }

}
