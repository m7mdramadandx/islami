package com.ramadan.islami.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal
import com.ramadan.islami.ui.activity.*
import com.ramadan.islami.ui.fragment.Dashboard

class NotificationOpenedHandler(base: Context?) : OneSignal.OSNotificationOpenedHandler,
    ContextWrapper(base) {

    override fun notificationOpened(result: OSNotificationOpenedResult?) {
        val data = result!!.notification.additionalData
        when (data["intentName"]) {
            "topic" -> Intent(applicationContext, TopicDetails::class.java)
            "video" -> Intent(applicationContext, VideoList::class.java)
            "story" -> Intent(applicationContext, StoryDetails::class.java)
            "ramadan" -> Intent(applicationContext, Quote::class.java)
            "hadiths" -> Intent(applicationContext, Hadiths::class.java)
            "hadithOfDay" -> Intent(applicationContext, QuoteOfDay::class.java)
            else -> Intent(applicationContext, Dashboard::class.java)
        }.apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("intentKey", "topic")
            putExtra("documentID", data["documentID"].toString())
            putExtra("collectionID", data["collectionID"].toString())
            startActivity(this)
        }
    }
}