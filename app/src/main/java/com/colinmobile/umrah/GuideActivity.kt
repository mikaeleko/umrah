package com.colinmobile.umrah

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.PendingIntent
import android.content.*
import android.content.pm.PackageManager
import android.net.ParseException
import android.net.sip.*
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.TimeUnit


class GuideActivity : AppCompatActivity(){
    var sipAddress: String? = null

    var manager: SipManager? = null
    var me: SipProfile? = null
    var call: SipAudioCall? = null
    var callReceiver: IncomingCallReceiver? = null

    private val CALL_ADDRESS = 1
    private val SET_AUTH_INFO = 2
    private val UPDATE_SETTINGS_DIALOG = 3
    private val HANG_UP = 4
    var broadcastReceiver: BroadcastReceiver? = null
    var Microphone_Plugged_in = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        title = resources.getString(R.string.live_guide_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        checkPermission(Manifest.permission.USE_SIP, 10)
        checkPermission(Manifest.permission.RECORD_AUDIO, 20)
        checkPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS, 10)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val action = intent.action
                val iii: Int
                if (Intent.ACTION_HEADSET_PLUG == action) {
                    iii = intent.getIntExtra("state", -1)
                    if (iii == 0) {
                        Microphone_Plugged_in = false
                        Toast.makeText(
                            applicationContext,
                            "headset tidak terpasang",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if (iii == 1) {
                        Microphone_Plugged_in = true
                        Toast.makeText(
                            applicationContext, "headset terpasang",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        val receiverFilter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
        registerReceiver(broadcastReceiver, receiverFilter)

        val filter = IntentFilter()
        filter.addAction("android.SipDemo.INCOMING_CALL")
        callReceiver = IncomingCallReceiver()
        this.registerReceiver(callReceiver, filter)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//        initializeManager()
//        val intent = Intent(this, IncomingCallService::class.java)
//        startService(intent)

        val loginLayout = findViewById<RelativeLayout>(R.id.login_layout)
        val sipLayout = findViewById<RelativeLayout>(R.id.sip_layout)

        val username = findViewById<AppCompatEditText>(R.id.sip_username)
        val password = findViewById<AppCompatEditText>(R.id.sip_password)
        val btnLogin = findViewById<AppCompatButton>(R.id.btn_login)
        btnLogin.setOnClickListener({
            if (password.text.toString().equals("UmHajj" + username.text.toString())) {
                if (manager == null) {
                    manager = SipManager.newInstance(this)
                }
//                initializeLocalProfile()

                if (me != null) {
                    closeLocalProfile()
                }

                val prefs = PreferenceManager.getDefaultSharedPreferences(baseContext).edit()
                prefs.putString("namePref", username.text.toString())
//        val domain = prefs.getString("domainPref", "")
                prefs.putString("passPref", "UmHajj" + username.text.toString())
                prefs.apply()

                try {
                    val builder = SipProfile.Builder(
                        username.text.toString(),
                        resources.getString(R.string.SERVER_URL)
                    )
                    builder.setPassword(password.text.toString())
                    me = builder.build()
                    val i = Intent()
                    i.action = "android.SipDemo.INCOMING_CALL"
                    val pi = PendingIntent.getBroadcast(this, 0, i, Intent.FILL_IN_DATA)
                    manager!!.open(me, pi, null)
                    loginLayout.visibility = View.GONE
                    sipLayout.visibility = View.VISIBLE

                    // This listener must be added AFTER manager.open is called,
                    // Otherwise the methods aren't guaranteed to fire.
                    manager!!.setRegistrationListener(
                        me!!.getUriString(),
                        object : SipRegistrationListener {
                            override fun onRegistering(localProfileUri: String) {
                                updateStatus("Registering with SIP Server...")
                            }

                            override fun onRegistrationDone(
                                localProfileUri: String,
                                expiryTime: Long
                            ) {
                                updateStatus(username.text.toString() + " ready")
                            }

                            override fun onRegistrationFailed(
                                localProfileUri: String, errorCode: Int,
                                errorMessage: String
                            ) {
//                        Toast.makeText(
//                            this@GuideActivity,
//                            resources.getText(R.string.registration_failed),
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()
                                updateStatus("Registration failed.  Please check settings.")
                            }
                        })
                } catch (pe: ParseException) {
//                Toast.makeText(
//                    this@GuideActivity,
//                    resources.getText(R.string.connection_error),
//                    Toast.LENGTH_SHORT
//                )
//                    .show()
                    updateStatus("Connection Error.")
                } catch (se: SipException) {
//                Toast.makeText(
//                    this@GuideActivity,
//                    resources.getText(R.string.connection_error),
//                    Toast.LENGTH_SHORT
//                )
//                    .show()
                    updateStatus("Connection error.")
                }
            } else {
                Toast.makeText(
                    this@GuideActivity,
                    "username dan password tidak sesuai",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        val btnLogout = findViewById<AppCompatButton>(R.id.log_out)
        btnLogout.setOnClickListener({
            if (call != null) {
                try {
                    call!!.endCall()
                    updateStatus("Ready")
                } catch (se: SipException) {
                    Log.d(
                        "onOptionsItemSelected",
                        "Error ending call.", se
                    )
                }
                call!!.close()
            }
            closeLocalProfile()
            if (callReceiver != null) {
                unregisterReceiver(callReceiver)
            }
            loginLayout.visibility = View.VISIBLE
            sipLayout.visibility = View.GONE
        })

        val sipCall = findViewById<AppCompatButton>(R.id.sip_call)
        val endCall = findViewById<AppCompatButton>(R.id.end_call)

        sipCall.setOnClickListener({
            val textField = findViewById(R.id.sip_destination) as EditText
            sipAddress = textField.text.toString()
            initiateCall()
        })
        endCall.setOnClickListener({ HANG_UP ->
            if (call != null) {
                try {
                    call!!.endCall()
                    updateStatus("Ready")
//                    setCallButton(View.VISIBLE, View.GONE)
                } catch (se: SipException) {
                    Log.d(
                        "onOptionsItemSelected",
                        "Error ending call.", se
                    )
                }
                call!!.close()
            }
        })

        val sendPin = findViewById<AppCompatButton>(R.id.send_pin)
        sendPin.setOnClickListener({
            var code = 0;
            val prefs =
                PreferenceManager.getDefaultSharedPreferences(baseContext)
            val username = prefs.getString("namePref", "")
            if (username.equals("10001")) {
                code = 1
            } else {
                code = 2
            }
            call?.sendDtmf(code)
            try {
                TimeUnit.SECONDS.sleep(2)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            call?.sendDtmf(code)
            try {
                TimeUnit.SECONDS.sleep(2)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            call?.sendDtmf(code)
            try {
                TimeUnit.SECONDS.sleep(2)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            call?.sendDtmf(code)
            try {
                TimeUnit.SECONDS.sleep(2)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            call?.startAudio()
            if(Microphone_Plugged_in){
                call?.setSpeakerMode(false)
            }else{
                call?.setSpeakerMode(true)
            }
            call?.toggleMute()
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        // When we get back from the preference setting Activity, assume
        // settings have changed, and re-login with new auth info.
        val filter = IntentFilter()
        filter.addAction("android.SipDemo.INCOMING_CALL")
        callReceiver = IncomingCallReceiver()
        this.registerReceiver(callReceiver, filter)
        initializeManager()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (call != null) {
            call!!.close()
        }
        closeLocalProfile()
        if (callReceiver != null) {
            unregisterReceiver(callReceiver)
        }
    }

    fun initializeManager() {
        if (manager == null) {
            manager = SipManager.newInstance(this)
        }
        initializeLocalProfile()
    }

    /**
     * Logs you into your SIP provider, registering this device as the location to
     * send SIP calls to for your SIP address.
     */
    fun initializeLocalProfile() {
        if (manager == null) {
            return
        }
        if (me != null) {
            closeLocalProfile()
        }
        val prefs =
            PreferenceManager.getDefaultSharedPreferences(baseContext)
        val username = prefs.getString("namePref", "")
//        val domain = prefs.getString("domainPref", "")
        val password = prefs.getString("passPref", "")
        val loginLayout = findViewById<RelativeLayout>(R.id.login_layout)
        val sipLayout = findViewById<RelativeLayout>(R.id.sip_layout)

        if (username!!.length == 0 || password!!.length  == 0) {
//            showDialog(UPDATE_SETTINGS_DIALOG)
            loginLayout.visibility = View.VISIBLE
            sipLayout.visibility = View.GONE
//            showDialog(this)
            return
        }else{
            loginLayout.visibility = View.GONE
            sipLayout.visibility = View.VISIBLE
        }
        try {
            val builder = SipProfile.Builder(
                username.toString(),
                resources.getString(R.string.SERVER_URL)
            )
            builder.setPassword(password.toString())
            me = builder.build()
            val i = Intent()
            i.action = "android.SipDemo.INCOMING_CALL"
            val pi = PendingIntent.getBroadcast(this, 0, i, Intent.FILL_IN_DATA)
            manager!!.open(me, pi, null)

            // This listener must be added AFTER manager.open is called,
            // Otherwise the methods aren't guaranteed to fire.
            manager!!.setRegistrationListener(
                me!!.getUriString(),
                object : SipRegistrationListener {
                    override fun onRegistering(localProfileUri: String) {
                        updateStatus("Registering with SIP Server...")
                    }

                    override fun onRegistrationDone(
                        localProfileUri: String,
                        expiryTime: Long
                    ) {
                        updateStatus(username + " ready")
                    }

                    override fun onRegistrationFailed(
                        localProfileUri: String, errorCode: Int,
                        errorMessage: String
                    ) {
                        updateStatus("Registration failed.  Please check settings.")
                    }
                })
        } catch (pe: ParseException) {
            updateStatus("Connection Error.")
        } catch (se: SipException) {
            updateStatus("Connection error.")
        }
    }

    // This function is called when user accept or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when user is prompt for permission.

    // This function is called when user accept or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when user is prompt for permission.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super
            .onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        if (requestCode == 10) {
            // Checking whether user granted the permission or not.
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {

                // Showing the toast message
                Toast.makeText(
                    this@GuideActivity,
                    "SIP Permission Granted",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                Toast.makeText(
                    this@GuideActivity,
                    "SIP Permission Denied",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }else
        if (requestCode == 20) {
            // Checking whether user granted the permission or not.
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {

                // Showing the toast message
                Toast.makeText(
                    this@GuideActivity,
                    "SIP Permission Granted",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                Toast.makeText(
                    this@GuideActivity,
                    "SIP Permission Denied",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }


    fun checkPermission(permission: String, requestCode: Int) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                this@GuideActivity,
                permission
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat
                .requestPermissions(
                    this@GuideActivity, arrayOf(permission),
                    requestCode
                )
        } else {
            Toast
                .makeText(
                    this@GuideActivity,
                    "Permission already granted",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }

    /**
     * Closes out your local profile, freeing associated objects into memory
     * and unregistering your device from the server.
     */
    fun closeLocalProfile() {
        if (manager == null) {
            return
        }
        try {
            if (me != null) {
                manager!!.close(me!!.uriString)
            }
        } catch (ee: Exception) {
            Log.d("onDestroy", "Failed to close local profile.", ee)
        }
    }

    /**
     * Make an outgoing call.
     */
    fun initiateCall() {
        updateStatus(sipAddress)
        try {
            val listener: SipAudioCall.Listener = object : SipAudioCall.Listener() {
                // Much of the client's interaction with the SIP Stack will
                // happen via listeners.  Even making an outgoing call, don't
                // forget to set up a listener to set things up once the call is established.
                override fun onCallEstablished(call: SipAudioCall) {
                    call.startAudio()
                    if(Microphone_Plugged_in){
                        call?.setSpeakerMode(false)
                    }else{
                        call?.setSpeakerMode(true)
                    }
                    call.toggleMute()
                    updateStatus(call)
//                    setCallButton(View.GONE, View.VISIBLE)
                }

                override fun onCallEnded(call: SipAudioCall) {
                    updateStatus("Ready.")
//                    setCallButton(View.VISIBLE, View.GONE)
                }
            }

            call = manager!!.makeAudioCall(
                me!!.uriString,
                sipAddress + "@" + resources.getString(R.string.SERVER_URL),
                listener,
                120
            )
        } catch (e: Exception) {
            Log.i("InitiateCall", "Error when trying to close manager.", e)
            if (me != null) {
                try {
                    manager!!.close(me!!.uriString)
//                    setCallButton(View.VISIBLE, View.GONE)
                } catch (ee: Exception) {
                    Log.i(
                        "InitiateCall",
                        "Error when trying to close manager.", ee
                    )
                    ee.printStackTrace()
                }
            }
            if (call != null) {
                call!!.close()
//                setCallButton(View.VISIBLE, View.GONE)
            }
        }
    }

    /**
     * Updates the status box at the top of the UI with a messege of your choice.
     * @param status The String to display in the status box.
     */
    fun updateStatus(status: String?) {
        // Be a good citizen.  Make sure UI changes fire on the UI thread.
        runOnUiThread {
            val labelView = findViewById(R.id.sipLabel) as TextView
            labelView.text = status
        }
    }

//    fun setCallButton(sipStatus:Int, endStatus:Int){
//        val sipCall = findViewById<AppCompatButton>(R.id.sip_call)
//        val sendPin = findViewById<AppCompatButton>(R.id.send_pin)
//        sip_call.visibility = sipStatus
//        send_pin.visibility = endStatus
//    }

    /**
     * Updates the status box with the SIP address of the current call.
     * @param call The current, active call.
     */
    fun updateStatus(call: SipAudioCall) {
        var useName = call.peerProfile.displayName
        if (useName == null) {
            useName = call.peerProfile.userName
        }
        updateStatus(useName)
//        if(call!=null){
//            setCallButton(View.GONE, View.VISIBLE)
//        }else{
//            setCallButton(View.VISIBLE, View.GONE)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menu.add(0, SET_AUTH_INFO, 0, "Edit your SIP Info.")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            SET_AUTH_INFO -> {
                updatePreferences()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            CALL_ADDRESS -> {
                val factory = LayoutInflater.from(this)
                val textBoxView: View = factory.inflate(R.layout.call_address_dialog, null)
                return AlertDialog.Builder(this)
                    .setTitle("Call Someone.")
                    .setView(textBoxView)
                    .setPositiveButton(
                        "OK",
                        DialogInterface.OnClickListener { dialog, whichButton ->
                            val textField =
                                textBoxView.findViewById(R.id.calladdress_edit) as EditText
                            sipAddress = textField.text.toString()
                            initiateCall()
                        })
                    .setNegativeButton(
                        "Cancel",
                        DialogInterface.OnClickListener { dialog, whichButton ->
                            // Noop.
                        })
                    .create()
            }
            UPDATE_SETTINGS_DIALOG -> return AlertDialog.Builder(this)
                .setMessage("Please update your SIP Account Settings.")
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, whichButton -> updatePreferences() })
                .setNegativeButton(
                    "Cancel",
                    DialogInterface.OnClickListener { dialog, whichButton ->
                        // Noop.
                    })
                .create()
        }
        return null
    }

    fun updatePreferences() {
        val settingsActivity = Intent(
            baseContext,
            SipSettings::class.java
        )
        startActivity(settingsActivity)
    }

}