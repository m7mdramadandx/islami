package com.ramadan.islami

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AutoStart : BroadcastReceiver() {
    var azan = Azan()
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            azan.setAlarm(context)
        }
    }
}