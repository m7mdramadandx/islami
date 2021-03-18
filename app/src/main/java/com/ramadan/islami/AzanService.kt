package com.ramadan.islami

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AzanService : Service() {
    var alarm = Azan()
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        alarm.setAlarm(this)
        return START_STICKY
    }

    override fun onStart(intent: Intent, startId: Int) {
        alarm.setAlarm(this)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}