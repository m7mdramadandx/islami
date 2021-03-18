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
import com.ramadan.islami.Azan
import com.ramadan.islami.R
import com.ramadan.islami.ui.activity.PrayerTimes


class NotificationHelper(base: Context) : ContextWrapper(base) {

    private val pendingIntent = PendingIntent.getActivity(
        this, 0, Intent(this, PrayerTimes::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT
    ).apply {
        Azan().cancelAlarm(this@NotificationHelper)
        showMessage(this@NotificationHelper, "CANCELED")
    }


    val manager: NotificationManager =
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel =
            NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
        manager.createNotificationChannel(channel)
    }

    fun channelNotification(title: String, message: String): Notification =
        NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_notification)
            .setFullScreenIntent(pendingIntent, true)
            .addAction(R.mipmap.ic_launcher, "Okay", pendingIntent)
            .build()

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