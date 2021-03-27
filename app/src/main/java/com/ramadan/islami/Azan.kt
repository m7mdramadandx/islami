package com.ramadan.islami

import android.annotation.SuppressLint
import android.app.AlarmManager
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
import com.ramadan.islami.utils.*
import java.util.*


class Azan : BroadcastReceiver() {

    @SuppressLint("InvalidWakeLockTag", "UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val powerManager: PowerManager =
            context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock: PowerManager.WakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            ""
        )
        wakeLock.acquire(10 * 60 * 1000L /*10 minutes*/)
        mediaPlayer = MediaPlayer.create(context, R.raw.elqtamy).apply {
            setVolume(100 * .01f, 100 * .01f)
            start()
            setOnCompletionListener { release() }
        }
        val notificationHelper = NotificationHelper(context)
        val notification = notificationHelper.channelNotification(
            " صلاه $prayName",
            context.getString(R.string.notificationVerse)
        )
        notificationHelper.manager.notify(1001, notification)
        wakeLock.release()
    }

    fun setAlarm(context: Context) {
        val calendar = getAlarmDate(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Azan::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT)
        val componentName = ComponentName(context, Azan::class.java)
        val packageManager = context.packageManager
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            calendar?.let {
                if ((Calendar.getInstance().timeInMillis <= calendar.timeInMillis)
                    || (Calendar.getInstance().time.seconds <= calendar.timeInMillis)
                ) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                    showMessage(context, "Next Alarm: ${calendar.time.minutes}")
                    Log.e(debug_tag, "Next Alarm: ${calendar.time.minutes}")
                }
            }
        }
    }

    fun getAlarmDate(context: Context): Calendar? {
        val calendar = Calendar.getInstance()
        localeHelper.getPrayerTimes(context)?.let { mutableSet ->
            var setAlarm = false
            val hourTime = mutableListOf(
                "6",
                "6",
                "6",
//                mutableSet.find { it.contains("fajr") }?.substring(0, 2),
//                mutableSet.find { it.contains("dhuhr") }?.substring(0, 2),
//                mutableSet.find { it.contains("asr") }?.substring(0, 2),
//                mutableSet.find { it.contains("maghrib") }?.substring(0, 2),
//                mutableSet.find { it.contains("isha") }?.substring(0, 2),
            )
            val minutesTime = mutableListOf(
                "1",
                "4",
                "6",
//                mutableSet.find { it.contains("fajr") }?.substring(3, 5),
//                mutableSet.find { it.contains("dhuhr") }?.substring(3, 5),
//                mutableSet.find { it.contains("asr") }?.substring(3, 5),
//                mutableSet.find { it.contains("maghrib") }?.substring(3, 5),
//                mutableSet.find { it.contains("isha") }?.substring(3, 5),
            )
            var hour = hourTime[0]?.toInt()
            var minute = minutesTime[0]?.toInt()
            val currentHour = calendar[Calendar.HOUR_OF_DAY]
            val mm = calendar[Calendar.MINUTE]
            for (i in 0 until hourTime.size) {
                if (currentHour <= hourTime[i]!!.toInt() && mm <= minutesTime[i]!!.toInt() && !setAlarm) {
                    hour = hourTime[i]?.toInt()
                    minute = minutesTime[i]?.toInt()
                    setAlarm = true
                    prayName = Utils(context).prayers[i]
                } else if (i == hourTime.size - 1 && !setAlarm) {
                    calendar.add(Calendar.DATE, 1)
                    hour = hourTime[0]?.toInt()
                    minute = minutesTime[0]?.toInt()
                }
            }
            calendar[Calendar.HOUR_OF_DAY] = hour!!
            calendar[Calendar.MINUTE] = minute!!
            calendar[Calendar.SECOND] = 0
            return calendar
        } ?: return null
    }

    fun cancelAlarm(context: Context) {
        val componentName = ComponentName(context, Azan::class.java)
        val packageManager = context.packageManager
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        val intent = Intent(context, Azan::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        showMessage(context, "Canceled")
    }

    companion object {
        val localeHelper = LocaleHelper()
        var prayName = ""
        var mediaPlayer = MediaPlayer()
    }
}