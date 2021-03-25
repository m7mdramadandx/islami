package com.ramadan.islami.utils


import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.view.WindowManager
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
        val intent = Intent(this, AzanActivity::class.java).apply {
            putExtra("prayName", title)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT
        )


        return NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(title))
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setShowWhen(true)
            .setFullScreenIntent(pendingIntent, true)
            .build()
    }

    fun Activity.turnScreenOffAndKeyguardOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(false)
            setTurnScreenOn(false)
        } else {
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            )
        }
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