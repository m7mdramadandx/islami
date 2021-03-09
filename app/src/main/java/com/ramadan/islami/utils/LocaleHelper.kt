@file:Suppress("DEPRECATION")

package com.ramadan.islami.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager.getDefaultSharedPreferences
import java.util.*
import kotlin.collections.HashSet


class LocaleHelper {
    companion object {
        private const val SELECTED_LANGUAGE = "default_language"
        private const val SELECTED_THEME = "default_theme"
        private const val STORY_MARKS = "story_marks"
        private const val QURAN_MARKS = "quran_marks"
        private const val HADITH_OF_DAY = "hadith_of_day"
        private const val VERSE_OF_DAY = "verse_of_day"
    }

    fun getAyahFontSize(context: Context): String? {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        return prefs.getString("ayah_text_preference", null)
    }

    fun setHadithOfDay(context: Context, hadith: String?) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putString(HADITH_OF_DAY, hadith)
        editor.apply()
    }

    fun getHadithOfDay(context: Context): String {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getString(HADITH_OF_DAY, " ").toString()
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


    fun setQuranMark(context: Context, part: String) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val newMark = HashSet<String>()
        newMark.addAll(getQuranMark(context))
        newMark.add(part)
        editor.putStringSet(QURAN_MARKS, newMark)
        editor.clear().apply()
    }

    fun getQuranMark(context: Context): MutableSet<String> {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getStringSet(QURAN_MARKS, emptySet()) as MutableSet<String>
    }

    fun setStoryMark(context: Context, part: String) {
        val prefs: SharedPreferences = getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val newMark = HashSet<String>()
        newMark.addAll(getStoryMark(context))
        newMark.add(part)
        editor.putStringSet(STORY_MARKS, newMark)
        editor.clear().apply()
    }

    fun getStoryMark(context: Context): MutableSet<String> {
        val prefs = getDefaultSharedPreferences(context)
        return prefs.getStringSet(STORY_MARKS, emptySet())!!.toMutableSet()
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
