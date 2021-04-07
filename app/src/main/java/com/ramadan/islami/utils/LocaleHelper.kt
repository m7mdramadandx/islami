@file:Suppress("DEPRECATION")

package com.ramadan.islami.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager.getDefaultSharedPreferences
import com.ramadan.islami.data.model.Prayer
import com.ramadan.islami.data.model.Verse
import java.util.*
import kotlin.collections.HashSet

class LocaleHelper {
    companion object {
        private const val SELECTED_LANGUAGE = "default_language"
        private const val SELECTED_THEME = "default_theme"
        private const val NOTIFICATION = "notification"
        private const val AZAN = "azan"
        private const val STORY_MARKS = "story_marks"
        private const val QURAN_MARK = "quran_mark"
        private const val VERSE_OF_DAY = "verse_of_day"
        private const val HADITH_OF_DAY = "hadith_of_day"
        private const val Prayer_TIMES = "prayer_times"
        private const val NEXT_Prayer = "next_prayer"
    }

    fun setVerseOfDay(context: Context, verseItem: Verse.VerseItem) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val verse = HashSet<String>()
        verse.addAll(
            mutableSetOf(
                verseItem.surah + " surah",
                verseItem.ayah + "\n" + verseItem.translation + " ayah",
                dateOfDay() + " date",
            )
        )
        prefs.edit().apply {
            putStringSet(VERSE_OF_DAY, verse)
            apply()
        }
    }

    fun getVerseOfDay(context: Context): MutableSet<String> {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getStringSet(VERSE_OF_DAY, mutableSetOf())!!.toMutableSet()
    }

    fun setHadithOfDay(context: Context, hadithBody: String, chapterTitle: String) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val hadith = HashSet<String>()
        hadith.addAll(
            mutableSetOf(
                "$chapterTitle title",
                "$hadithBody body",
                dateOfDay() + " date"
            )
        )
        prefs.edit().apply {
            putStringSet(HADITH_OF_DAY, hadith)
            apply()
        }
    }

    fun getHadithOfDay(context: Context): MutableSet<String> {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getStringSet(HADITH_OF_DAY, mutableSetOf())!!.toMutableSet()
    }

    fun setPrayerTimes(context: Context, pray: Prayer.Timings) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val prayerTimes = HashSet<String>()
        prayerTimes.addAll(
            mutableSetOf(
                pray.fajr.plus(" fajr"),
                pray.sunrise.plus(" sunrise"),
                pray.dhuhr.plus(" dhuhr"),
                pray.asr.plus(" asr"),
                pray.maghrib.plus(" maghrib"),
                pray.isha.plus(" isha"),
            )
        )
        prefs.edit().apply {
            putStringSet(Prayer_TIMES, prayerTimes)
            apply()
        }
    }

    fun getPrayerTimes(context: Context): MutableSet<String>? {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getStringSet(Prayer_TIMES, null)
    }

    fun setNextPrayer(context: Context, prayer: Int) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        prefs.edit().apply {
            putInt(NEXT_Prayer, prayer)
            apply()
        }
    }

    fun getNextPrayer(context: Context): Int {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getInt(NEXT_Prayer, 0)
    }

    fun setQuranMark(context: Context, page: String) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        prefs.edit().apply {
            putString(QURAN_MARK, page)
            apply()
        }
    }

    fun getQuranMark(context: Context): String? {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getString(QURAN_MARK, "سورة الفاتحة - صفحة رقم 1")
    }

    fun setStoryMark(context: Context, part: String) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val newMark = HashSet<String>()
        newMark.addAll(getStoryMark(context))
        newMark.add(part)
        prefs.edit().apply {
            putStringSet(STORY_MARKS, newMark)
            apply()
        }
    }

    fun getStoryMark(context: Context): MutableSet<String> {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getStringSet(STORY_MARKS, mutableSetOf())!!.toMutableSet()
    }

    fun setNotification(context: Context, notification: Boolean) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        prefs.edit().apply {
            putBoolean(NOTIFICATION, notification)
            apply()
        }
    }

    fun getNotification(context: Context): Boolean {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getBoolean(NOTIFICATION, true)
    }

    fun setAzan(context: Context, azan: Boolean) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        prefs.edit().apply {
            putBoolean(AZAN, azan)
            apply()
        }
    }

    fun getAzan(context: Context): Boolean {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getBoolean(AZAN, true)
    }


    fun setTheme(context: Context, theme: String?) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        prefs.edit().apply {
            putString(SELECTED_THEME, theme)
            apply()
        }
    }

    fun getDefaultTheme(context: Context): String? {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getString(SELECTED_THEME, "light")
    }

    fun setLocale(context: Context, language: String): Context {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
    }

    fun persist(context: Context, language: String) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        prefs.edit().apply {
            putString(SELECTED_LANGUAGE, language)
            apply()
        }
    }

    fun getDefaultLanguage(context: Context): String? {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getString(SELECTED_LANGUAGE, "ar")
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = context.resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}