package com.ramadan.islami.utils


import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ramadan.islami.R
import com.ramadan.islami.ui.activity.AzanActivity


class NotificationHelper(base: Context) : ContextWrapper(base) {


    val manager: NotificationManager =
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel =
            NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
        manager.createNotificationChannel(channel)
    }

    fun channelNotification(title: String, message: String): Notification {
        val clickIntent = Intent(this, AzanActivity::class.java).apply {
            putExtra("prayName", title)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, clickIntent, PendingIntent.FLAG_ONE_SHOT
        )

        return NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setShowWhen(true)
//            .addAction(R.mipmap.ic_launcher, "NOO", pendingIntent)
//            .setContentIntent(pendingIntent)
            .setFullScreenIntent(pendingIntent, true)
            .addAction(R.mipmap.ic_launcher, "Okay", pendingIntent)
            .build()
    }


    companion object {
        const val channelID = "channelID"
        const val channelName = "Channel Name"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }
}