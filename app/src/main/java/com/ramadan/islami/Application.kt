package com.ramadan.islami

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.google.android.gms.ads.MobileAds
import com.onesignal.OneSignal
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.NotificationOpenedHandler


const val ONESIGNAL_APP_ID = "84b5b5b5-1be2-49c4-b7cc-dc033da3bf84"

class Application : Application() {
    private val localeHelper = LocaleHelper()

    override fun onCreate() {
        super.onCreate()
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
//        OneSignal.OSRemoteNotificationReceivedHandler(NotificationReceived())
        OneSignal.setNotificationOpenedHandler(NotificationOpenedHandler(this))
        OneSignal.pauseInAppMessages(false)
        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        MobileAds.initialize(this, getString(R.string.ad_id))

        Azan().setAlarm(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(
            localeHelper.setLocale(
                base!!,
                localeHelper.getDefaultLanguage(base)
            )
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeHelper.setLocale(this, localeHelper.getDefaultLanguage(this))
    }

}