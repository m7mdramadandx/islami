@file:Suppress("DEPRECATION")

package com.ramadan.islami.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager.getDefaultSharedPreferences
import java.util.*


class LocaleHelper {
    companion object {
        private const val SELECTED_LANGUAGE = "default_language"
        private const val SELECTED_THEME = "default_theme"
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
        return prefs.getString(SELECTED_LANGUAGE, "en").toString()
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
