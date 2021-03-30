package com.ramadan.islami

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.onesignal.OneSignal
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.NotificationOpenedHandler
import com.ramadan.islami.utils.ONESIGNAL_APP_ID


class Application : Application() {
    private val localeHelper = LocaleHelper()
    var azan = Azan()
    override fun onCreate() {
        super.onCreate()
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.setNotificationOpenedHandler(NotificationOpenedHandler(this))
        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        val conf = RequestConfiguration.Builder()
//            .setTestDeviceIds(listOf("95B73043073F86AAE72BC71577E335B0",
//                "A8F03AB6E0110E58FD4ABD5A2ED25318"))
//            .setMaxAdContentRating(MAX_AD_CONTENT_RATING_T)
            .build()
        MobileAds.setRequestConfiguration(conf)
        @Suppress("DEPRECATION")
        MobileAds.initialize(this, getString(R.string.ad_id))
        if (localeHelper.getAzan(this)) azan.setAlarm(this)
    }

}