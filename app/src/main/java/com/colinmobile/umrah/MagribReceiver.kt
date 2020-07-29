package com.colinmobile.umrah

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.core.app.BundleCompat
import androidx.core.app.NotificationCompat


class MagribReceiver : BroadcastReceiver() {
//    var databaseHelper: DatabaseHelper? = null

    override fun onReceive(context: Context, intent: Intent?) {
        var bundle : Bundle?=intent?.extras
        val quote = intent?.getStringExtra("message")
        val `when` = System.currentTimeMillis()
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(context, SalatActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(
            context, 0,
            notificationIntent, PendingIntent.FLAG_ONE_SHOT
        )
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mNotifyBuilder = NotificationCompat.Builder(
            context
        ).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Live UmHajj")
            .setContentText(quote).setSound(alarmSound)
            .setAutoCancel(true).setWhen(`when`)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.mipmap.ic_launcher
                )
            )
            .setStyle(NotificationCompat.BigTextStyle().bigText(quote))
            .setContentIntent(pendingIntent)
            .setVibrate(
                longArrayOf(
                    1000,
                    1000,
                    1000,
                    1000,
                    1000
                )
            ) // Declair VIBRATOR Permission in AndroidManifest.xml
        notificationManager.notify(5, mNotifyBuilder.build())
    }
}