package com.ramadan.islami.utils

import android.content.Context
import android.content.Intent

class BadgeUtils {

    fun setBadge(context: Context, count: Int) {
        setBadgeSamsung(context, count)
        setBadgeSony(context, count)
    }

    fun clearBadge(context: Context) {
        setBadgeSamsung(context, 0)
        clearBadgeSony(context)
    }

    private fun setBadgeSamsung(context: Context, count: Int) {
        val launcherClassName = getLauncherClassName(context)
            ?: return
        val intent = Intent("android.intent.action.BADGE_COUNT_UPDATE")
        intent.putExtra("badge_count", count)
        intent.putExtra("badge_count_package_name", context.packageName)
        intent.putExtra("badge_count_class_name", launcherClassName)
        context.sendBroadcast(intent)
    }

    private fun setBadgeSony(context: Context, count: Int) {
        val launcherClassName = getLauncherClassName(context)
            ?: return
        val intent = Intent()
        intent.action = "com.sonyericsson.home.action.UPDATE_BADGE"
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", launcherClassName)
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", true)
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", count.toString())
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME",
            context.packageName)
        context.sendBroadcast(intent)
    }

    private fun clearBadgeSony(context: Context) {
        val launcherClassName = getLauncherClassName(context)
            ?: return
        val intent = Intent()
        intent.action = "com.sonyericsson.home.action.UPDATE_BADGE"
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", launcherClassName)
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", false)
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", 0.toString())
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME",
            context.packageName)
        context.sendBroadcast(intent)
    }

    private fun getLauncherClassName(context: Context): String? {
        val pm = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val resolveInfos = pm.queryIntentActivities(intent, 0)
        for (resolveInfo in resolveInfos) {
            val pkgName = resolveInfo.activityInfo.applicationInfo.packageName
            if (pkgName.equals(context.packageName, ignoreCase = true)) {
                return resolveInfo.activityInfo.name
            }
        }
        return null
    }
}