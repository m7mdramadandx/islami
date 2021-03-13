@file:Suppress("DEPRECATION")

package com.ramadan.islami.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager.getDefaultSharedPreferences
import com.ramadan.islami.data.model.Azkar
import com.ramadan.islami.data.model.Prayer
import java.util.*
import kotlin.collections.HashSet


class LocaleHelper {
    companion object {
        private const val SELECTED_LANGUAGE = "default_language"
        private const val SELECTED_THEME = "default_theme"
        private const val STORY_MARKS = "story_marks"
        private const val QURAN_MARKS = "quran_marks"
        private const val VERSE_OF_DAY = "verse_of_day"
        private const val HADITH_OF_DAY = "hadith_of_day"
        private const val AZKAR_OF_DAY = "azkar_of_day"
        private const val AZKAR_OF_DAY1 = "azkar_of_day1"
        private const val Prayer_TIMES = "prayer_times"
    }

    fun setVerseOfDay(context: Context, verse: String?) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putString(VERSE_OF_DAY, verse)
        editor.apply()
    }

    fun getVerseOfDay(context: Context): String {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getString(VERSE_OF_DAY, " ").toString()
    }

    fun setHadithOfDay(context: Context, hadithBody: String, chapterTitle: String) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val hadith = HashSet<String>()
        hadith.addAll(
            mutableSetOf(
                chapterTitle,
                hadithBody,
                dateOfDay(),
            ).sortedDescending()
        )
        editor.remove(HADITH_OF_DAY)
        editor.putStringSet(HADITH_OF_DAY, hadith)
        editor.apply()
    }

    fun getHadithOfDay(context: Context): MutableSet<String> {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getStringSet(HADITH_OF_DAY, mutableSetOf())!!.toMutableSet()
    }

    fun setAzkarOfDay(context: Context, azkarItem: Azkar.AzkarItem) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val hadith = HashSet<String>()
        hadith.addAll(
            mutableSetOf(
                azkarItem.category + "title",
                azkarItem.zekr + "body",
                azkarItem.description + "description",
                azkarItem.reference + "reference",
                dateOfDay() + "date",
            )
        )
        editor.remove(AZKAR_OF_DAY)
        editor.putStringSet(AZKAR_OF_DAY, hadith)
        editor.apply()
    }

    fun getAzkarOfDay(context: Context): MutableSet<String> {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getStringSet(AZKAR_OF_DAY, mutableSetOf())!!.toMutableSet()
    }

    fun setAzkarOfDay1(context: Context, azkarItem: Azkar.AzkarItem) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val hadith = HashSet<String>()
        hadith.addAll(
            mutableSetOf(
                azkarItem.category + "title",
                azkarItem.zekr + "body",
                azkarItem.description + "description",
                azkarItem.reference + "reference",
                dateOfDay() + "date",
            )
        )
        editor.remove(AZKAR_OF_DAY1)
        editor.putStringSet(AZKAR_OF_DAY1, hadith)
        editor.apply()
    }

    fun getAzkarOfDay1(context: Context): MutableSet<String> {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getStringSet(AZKAR_OF_DAY1, mutableSetOf())!!.toMutableSet()
    }

    fun setPrayerTimes(context: Context, pray: Prayer.Timings) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val prayerTimes = HashSet<String>()
        prayerTimes.addAll(
            mutableSetOf(
                pray.fajr.removeSuffix("(EET)"),
                pray.sunrise.removeSuffix("(EET)"),
                pray.dhuhr.removeSuffix("(EET)"),
                pray.asr.removeSuffix("(EET)"),
                pray.maghrib.removeSuffix("(EET)"),
                pray.isha.removeSuffix("(EET)"),
            ).sortedDescending()
        )
        editor.remove(Prayer_TIMES)
        editor.putStringSet(Prayer_TIMES, prayerTimes)
        editor.apply()
    }

    fun getPrayerTimes(context: Context): MutableSet<String> {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getStringSet(Prayer_TIMES, mutableSetOf())!!.toMutableSet()
    }

    fun setQuranMark(context: Context, page: String) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val newMark = HashSet<String>()
        newMark.addAll(getQuranMark(context))
        newMark.add(page)
        editor.remove(QURAN_MARKS)
        editor.putStringSet(QURAN_MARKS, newMark)
        editor.apply()
    }

    fun getQuranMark(context: Context): MutableSet<String> {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getStringSet(QURAN_MARKS, mutableSetOf())!!.toMutableSet()
    }

    fun setStoryMark(context: Context, part: String) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val newMark = HashSet<String>()
        newMark.addAll(getStoryMark(context))
        newMark.add(part)
        editor.putStringSet(STORY_MARKS, newMark)
        editor.apply()
    }

    fun getStoryMark(context: Context): MutableSet<String> {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getStringSet(STORY_MARKS, mutableSetOf())!!.toMutableSet()
    }

    fun setTheme(context: Context, theme: String?) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putString(SELECTED_THEME, theme)
        editor.apply()
    }

    fun getDefaultTheme(context: Context): String {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getString(SELECTED_THEME, "light").toString()
    }

    fun setLocale(context: Context, language: String): Context {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
    }

    fun persist(context: Context, language: String) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    fun getDefaultLanguage(context: Context): String {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getString(SELECTED_LANGUAGE, "ar").toString()
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
