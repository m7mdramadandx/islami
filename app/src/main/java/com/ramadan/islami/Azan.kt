package com.ramadan.islami

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.PowerManager
import android.provider.Settings
import com.ramadan.islami.utils.NotificationHelper
import java.util.*


class Azan : BroadcastReceiver() {

    @SuppressLint("InvalidWakeLockTag")

    override fun onReceive(context: Context, intent: Intent) {

        val pm: PowerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl: PowerManager.WakeLock = pm.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            ""
        )
        wl.acquire(10 * 60 * 1000L /*10 minutes*/)
        val prayer = intent.getStringExtra("prayer").toString()
        val mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI)
        mediaPlayer.start()
        val notificationHelper = NotificationHelper(context)
        val nb: Notification = notificationHelper.channelNotification(prayer, "GOO")
        notificationHelper.manager.notify(Random().nextInt(), nb)
        wl.release()
    }

    fun setAlarm(context: Context) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(context, Azan::class.java).apply { putExtra("prayer", "AZAAN!") }
        val pi = PendingIntent.getBroadcast(context, 0, i, FLAG_UPDATE_CURRENT)
        val receiver: ComponentName = ComponentName(context, Azan::class.java)
        val pm = context.packageManager

        pm.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 21)
        calendar.set(Calendar.MINUTE, 9)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val cur: Calendar = Calendar.getInstance()
        if (cur.after(calendar)) calendar.add(Calendar.DATE, 1)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pi)
            am.setExact(AlarmManager.RTC, calendar.timeInMillis, pi)
//            am.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                System.currentTimeMillis(),
//                EVERY,
//                pi
//            ) // Millisec * Second * Minute

        }
    }

    fun cancelAlarm(context: Context) {
        val receiver: ComponentName = ComponentName(context, Azan::class.java)
        val pm = context.packageManager
        pm.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        val intent = Intent(context, Azan::class.java)
        val sender = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(sender)
    }
}