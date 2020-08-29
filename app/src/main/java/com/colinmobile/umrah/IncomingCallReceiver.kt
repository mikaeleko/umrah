package com.colinmobile.umrah

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.net.sip.SipAudioCall
import android.net.sip.SipProfile
import android.os.Bundle
import androidx.core.app.NotificationCompat


class IncomingCallReceiver : BroadcastReceiver() {
    var incomingCall: SipAudioCall? = null
    override fun onReceive(context: Context, intent: Intent?) {
//        var bundle : Bundle?=intent?.extras
//        var requestCode = intent?.getIntExtra("requestCode",0)
//        var requestCode = (0..1000).random()
//        val title = intent?.getStringExtra("title")
//        val quote = intent?.getStringExtra("message")
        val `when` = System.currentTimeMillis()
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(context, GuideActivity::class.java)
//        notificationIntent.putExtra("NotifClick", true)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(
            context, 7,
            notificationIntent, PendingIntent.FLAG_ONE_SHOT
        )
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mNotifyBuilder = NotificationCompat.Builder(
            context
        ).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Incoming Call")
            .setContentText("Incoming Call").setSound(alarmSound)
            .setAutoCancel(true).setWhen(`when`)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.mipmap.ic_launcher
                )
            )
            .setStyle(NotificationCompat.BigTextStyle().bigText("Incoming Call"))
            .setContentIntent(pendingIntent)
        notificationManager.notify(5, mNotifyBuilder.build())

        try {
            val listener: SipAudioCall.Listener = object : SipAudioCall.Listener() {
                override fun onRinging(call: SipAudioCall, caller: SipProfile) {
                    try {
                        call.answerCall(30)

//                        var bundle : Bundle?=intent?.extras
//        var requestCode = intent?.getIntExtra("requestCode",0)
//        var requestCode = (0..1000).random()
//        val title = intent?.getStringExtra("title")
//        val quote = intent?.getStringExtra("message")

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            val wtActivity: GuideActivity = context as GuideActivity
            incomingCall = wtActivity.manager?.takeAudioCall(intent, listener)
            wtActivity.call = incomingCall
//            wtActivity.updateStatus(incomingCall!!)
            if (incomingCall!!.isMuted) {
                incomingCall!!.toggleMute()
            }
//            val incomingCall = Intent(context, IncomingScreen::class.java)
//            incomingCall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(incomingCall)
            accept()
//            val wtActivity: GuideActivity = context as GuideActivity
//            incomingCall = wtActivity.manager?.takeAudioCall(intent, listener)
//            incomingCall?.answerCall(30)
//            incomingCall?.startAudio()
//            incomingCall?.setSpeakerMode(true)
//            if (incomingCall?.isMuted!!) {
//                incomingCall?.toggleMute()
//            }
//            wtActivity.call = incomingCall
//            wtActivity.updateStatus(incomingCall!!)

        } catch (e: Exception) {
            incomingCall?.close()
        }
    }

    fun accept(){
        try{
            incomingCall?.answerCall(30)
            incomingCall?.startAudio()
            incomingCall?.setSpeakerMode(true)
            if (incomingCall?.isMuted!!) {
                incomingCall?.toggleMute()
            }
        }catch (e: Exception) {
            incomingCall?.close()
        }
//            val wtActivity: GuideActivity = context as GuideActivity
//            incomingCall = wtActivity.manager?.takeAudioCall(intent, listener)
//
//            wtActivity.call = incomingCall
//            wtActivity.updateStatus(incomingCall!!)
    }

}