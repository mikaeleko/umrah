package com.colinmobile.umrah

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.sip.SipAudioCall
import android.net.sip.SipProfile
import android.view.View


class IncomingCallReceiver : BroadcastReceiver() {
    /**
     * Processes the incoming call, answers it, and hands it over to the
     * WalkieTalkieActivity.
     * @param context The context under which the receiver is running.
     * @param intent The intent being received.
     */
    override fun onReceive(context: Context, intent: Intent?) {
        var incomingCall: SipAudioCall? = null
        try {
            val listener: SipAudioCall.Listener = object : SipAudioCall.Listener() {
                override fun onRinging(call: SipAudioCall, caller: SipProfile) {
                    try {
                        call.answerCall(30)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            val wtActivity: GuideActivity = context as GuideActivity
            incomingCall = wtActivity.manager?.takeAudioCall(intent, listener)
            incomingCall?.answerCall(30)
            incomingCall?.startAudio()
            incomingCall?.setSpeakerMode(true)
            if (incomingCall?.isMuted!!) {
                incomingCall?.toggleMute()
            }
            wtActivity.call = incomingCall
            wtActivity.updateStatus(incomingCall!!)
        } catch (e: Exception) {
            incomingCall?.close()
        }
    }
}