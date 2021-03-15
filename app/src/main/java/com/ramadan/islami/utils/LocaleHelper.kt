@file:Suppress("DEPRECATION")

package com.ramadan.islami.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager.getDefaultSharedPreferences
import com.ramadan.islami.data.model.Azkar
import com.ramadan.islami.data.model.Prayer
import com.ramadan.islami.data.model.Verse
import java.util.*
import kotlin.collections.HashSet


class LocaleHelper {
    companion object {
        private const val SELECTED_LANGUAGE = "default_language"
        private const val SELECTED_THEME = "default_theme"
        private const val STORY_MARKS = "story_marks"
        private const val QURAN_MARK = "quran_marks"
        private const val VERSE_OF_DAY = "verse_of_day"
        private const val HADITH_OF_DAY = "hadith_of_day"
        private const val AZKAR_OF_DAY = "azkar_of_day"
        private const val AZKAR_OF_DAY1 = "azkar_of_day1"
        private const val Prayer_TIMES = "prayer_times"
    }

    fun setVerseOfDay(context: Context, verseItem: Verse.VerseItem) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val verse = HashSet<String>()
        verse.addAll(
            mutableSetOf(
                verseItem.surah + "surah",
                verseItem.ayah + "\n" + verseItem.translation + "ayah",
                dateOfDay() + "date",
            )
        )
        editor.remove(VERSE_OF_DAY)
        editor.putStringSet(VERSE_OF_DAY, verse)
        editor.apply()
    }

    fun getVerseOfDay(context: Context): MutableSet<String> {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getStringSet(VERSE_OF_DAY, mutableSetOf())!!.toMutableSet()
    }

    fun setHadithOfDay(context: Context, hadithBody: String, chapterTitle: String) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val hadith = HashSet<String>()
        hadith.addAll(
            mutableSetOf(
                chapterTitle + "title",
                hadithBody + "body",
                dateOfDay() + "date",
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
                pray.fajr.replace("(EET)", "fajr"),
                pray.sunrise.replace("(EET)", "sunrise"),
                pray.dhuhr.replace("(EET)", "dhuhr"),
                pray.asr.replace("(EET)", "asr"),
                pray.maghrib.replace("(EET)", "maghrib"),
                pray.isha.replace("(EET)", "isha"),
            )
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
        editor.putString(QURAN_MARK, page)
        editor.apply()
    }

    fun getQuranMark(context: Context): String {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getString(QURAN_MARK, null).toString()
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
        editor.remove(QURAN_MARK)
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
