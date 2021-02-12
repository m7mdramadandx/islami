package com.ramadan.islami.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal
import com.ramadan.islami.ui.activity.*


class NotificationOpenedHandler(base: Context?) : OneSignal.OSNotificationOpenedHandler,
    ContextWrapper(base) {

    override fun notificationOpened(result: OSNotificationOpenedResult?) {
        val data = result!!.notification.additionalData

        when (data["intentName"]) {
            "info" -> Intent(applicationContext, InformationList::class.java)
            "video" -> Intent(applicationContext, VideosList::class.java)
            "story" -> Intent(applicationContext, Story::class.java)
            "ramadan" -> Intent(applicationContext, Quote::class.java)
            "hadiths" -> Intent(applicationContext, Hadiths::class.java)
            else -> Intent(applicationContext, Dashboard::class.java)
        }.also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            it.putExtra("id", data["id"].toString())
            startActivity(it)
        }
    }
}