package com.colinmobile.umrah

import android.content.IntentFilter
import android.net.sip.SipAudioCall
import android.net.sip.SipManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class IncomingScreen : AppCompatActivity() {
    var manager: SipManager? = null
    var call: SipAudioCall? = null
    var service: IncomingCallService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incoming_screen)

        if (manager == null) {
            manager = service?.mSipManager
        }
        val filter = IntentFilter()
        filter.addAction("com.colinmobile.umrah.INCOMING_CALL")
        val callReceiver = IncomingCallReceiver()
        this.registerReceiver(callReceiver, filter)
        if(callReceiver!=null)
        {
            unregisterReceiver(callReceiver);
        }
        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras!=null && extras.getBoolean("NotiClick")) {
//                showAcceptReject(this)
                callReceiver?.accept()
            }
        }
    }
}