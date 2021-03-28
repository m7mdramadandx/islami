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
            "morningAzkar" -> Intent(applicationContext, TopicDetails::class.java)
            "eveningAzkar" -> Intent(applicationContext, TopicDetails::class.java)
            "azkar" -> Intent(applicationContext, TopicDetails::class.java)
            "topic" -> Intent(applicationContext, TopicDetails::class.java)
            "verseOfDay" -> Intent(applicationContext, QuoteOfDay::class.java)
            "hadithOfDay" -> Intent(applicationContext, QuoteOfDay::class.java)
            "video" -> Intent(applicationContext, VideoDetails::class.java)
            "story" -> Intent(applicationContext, StoryDetails::class.java)
            "ramadan" -> Intent(applicationContext, Quote::class.java)
            "hadiths" -> Intent(applicationContext, Hadiths::class.java)
            "quran" -> Intent(applicationContext, MainActivity::class.java)
            else -> Intent(applicationContext, MainActivity::class.java)
        }.apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("intentKey", data["intentKey"].toString())
            putExtra("documentID", data["documentID"].toString())
            putExtra("collectionID", data["collectionID"].toString())
            startActivity(this)
        }
    }
}