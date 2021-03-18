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
import android.util.Log
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.NotificationHelper
import com.ramadan.islami.utils.Utils
import com.ramadan.islami.utils.debug_tag
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
        mediaPlayer = MediaPlayer.create(context, R.raw.elqtamy)!!
        mediaPlayer.start()
        val notificationHelper = NotificationHelper(context)
        val nb: Notification = notificationHelper.channelNotification(prayName, "GOO")
        notificationHelper.manager.notify(1001, nb)
        wl.release()
    }

    fun setAlarm(context: Context) {
        val calendar = getAlarmDate(context)
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(context, Azan::class.java)
        val pi = PendingIntent.getBroadcast(context, 0, i, FLAG_UPDATE_CURRENT)
        val receiver: ComponentName = ComponentName(context, Azan::class.java)
        val pm = context.packageManager

        pm.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar!!.timeInMillis, pi)
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

    private fun getAlarmDate(context: Context): Calendar? {
        val calendar = Calendar.getInstance()
        var setAlarm = false
        val ALARM_HOUR_TIME = mutableListOf(
            localeHelper.getPrayerTimes(context).find { it.contains("fajr") }?.substring(0, 2),
            localeHelper.getPrayerTimes(context).find { it.contains("dhuhr") }?.substring(0, 2),
            localeHelper.getPrayerTimes(context).find { it.contains("asr") }?.substring(0, 2),
            localeHelper.getPrayerTimes(context).find { it.contains("maghrib") }?.substring(0, 2),
            localeHelper.getPrayerTimes(context).find { it.contains("isha") }?.substring(0, 2),
        )
        val ALARM_MINUTE_TIME = mutableListOf(
            localeHelper.getPrayerTimes(context).find { it.contains("fajr") }?.substring(3, 5),
            localeHelper.getPrayerTimes(context).find { it.contains("dhuhr") }?.substring(3, 5),
            localeHelper.getPrayerTimes(context).find { it.contains("asr") }?.substring(3, 5),
            localeHelper.getPrayerTimes(context).find { it.contains("maghrib") }?.substring(3, 5),
            localeHelper.getPrayerTimes(context).find { it.contains("isha") }?.substring(3, 5),
        )
        var hour: Int = ALARM_HOUR_TIME[0]!!.toInt()
        var minute: Int = ALARM_MINUTE_TIME[0]!!.toInt()
        val currentHour = calendar[Calendar.HOUR_OF_DAY]
        for (i in 0 until ALARM_HOUR_TIME.size) {
            if (currentHour <= ALARM_HOUR_TIME[i]!!.toInt() && !setAlarm) {
                hour = ALARM_HOUR_TIME[i]!!.toInt()
                minute = ALARM_MINUTE_TIME[i]!!.toInt()
                setAlarm = true
                prayName = Utils(context).prayers[i]
            } else if (i == ALARM_HOUR_TIME.size - 1 && !setAlarm) {
                calendar.add(Calendar.DATE, 1)
                hour = ALARM_HOUR_TIME[0]!!.toInt()
                minute = ALARM_MINUTE_TIME[0]!!.toInt()
            }
        }
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = 0
        Log.e(debug_tag, "Next Alarm: $hour:$minute")
        return calendar
    }

    companion object {
        val localeHelper = LocaleHelper()
        var prayName = ""
        var mediaPlayer = MediaPlayer()
    }
}