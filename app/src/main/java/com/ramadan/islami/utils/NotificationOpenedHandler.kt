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
            "hadithOfDay" -> Intent(applicationContext, HadithOfDay::class.java)
            else -> Intent(applicationContext, Dashboard::class.java)
        }.also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            it.putExtra("documentID", data["documentID"].toString())
            it.putExtra("collectionID", data["collectionID"].toString())
            startActivity(it)
        }
    }
}