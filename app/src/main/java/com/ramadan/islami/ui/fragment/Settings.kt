package com.ramadan.islami.ui.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.firebase.messaging.FirebaseMessaging
import com.onesignal.OneSignal
import com.ramadan.islami.Azan
import com.ramadan.islami.R
import com.ramadan.islami.ui.activity.MainActivity
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.showBrief

class Settings : Fragment() {
    private lateinit var localeHelper: LocaleHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        localeHelper = LocaleHelper()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<CardView>(R.id.language).setOnClickListener {
            alertDialog(
                getString(R.string.languageSetting),
                getString(R.string.arabic),
                getString(R.string.english),
                "language",
                view.context
            )
        }
        view.findViewById<CardView>(R.id.theme).setOnClickListener {
            alertDialog(
                getString(R.string.themeSetting),
                getString(R.string.lightTheme),
                getString(R.string.nightTheme),
                "theme",
                view.context
            )
        }
        view.findViewById<CardView>(R.id.notification).setOnClickListener {
            alertDialog(
                getString(R.string.notificationSetting),
                getString(R.string.subscribe),
                getString(R.string.unSubscribe),
                "notification",
                view.context
            )
        }
        view.findViewById<CardView>(R.id.azan).setOnClickListener {
            alertDialog(
                getString(R.string.azanSetting),
                getString(R.string.subscribe),
                getString(R.string.unSubscribe),
                "azan",
                view.context
            )
        }
        view.findViewById<CardView>(R.id.bookmarks).setOnClickListener {
            val quran =
                getString(R.string.quran) + " : " + localeHelper.getQuranMark(it.context) + "\n"
            val stories =
                getString(R.string.stories) + " : " + localeHelper.getStoryMark(it.context)
                    .toString() + "\n"
            showBrief(getString(R.string.readData), quran + stories, it.context)
        }
    }

    private fun alertDialog(
        title: String,
        optionOne: String,
        optionTwo: String,
        type: String,
        context: Context,
    ) {
        val dialogBuilder = AlertDialog.Builder(context)
        val view = View.inflate(context, R.layout.alert_dialog, null)
        dialogBuilder.setView(view)
        val alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        alertDialog.setCancelable(true)
        view.findViewById<TextView>(R.id.title).text = title
        val group = view.findViewById<RadioGroup>(R.id.group)
        val option1 = view.findViewById<RadioButton>(R.id.option1).also { it.text = optionOne }
        val option2 = view.findViewById<RadioButton>(R.id.option2).also { it.text = optionTwo }
        when (type) {
            "language" -> {
                if (localeHelper.getDefaultLanguage(context) == "ar") option1.isChecked =
                    true
                else option2.isChecked = true
                group.setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.option1 -> localeHelper.persist(context, "ar")
                        R.id.option2 -> localeHelper.persist(context, "en")
                    }
                    alertDialog.dismiss()
                    startActivity(Intent(context, MainActivity::class.java))
                }
            }
            "theme" -> {
                val option3 =
                    view.findViewById<RadioButton>(R.id.option3)
                        .also { it.visibility = View.VISIBLE }
                when (localeHelper.getDefaultTheme(context)) {
                    "light" -> option1.isChecked = true
                    "night" -> option2.isChecked = true
                    else -> option3.isChecked = true
                }
                group.setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.option1 -> {
                            localeHelper.setTheme(context, "light")
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        }
                        R.id.option2 -> {
                            localeHelper.setTheme(context, "night")
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        }
                        R.id.option3 -> {
                            localeHelper.setTheme(context, "follow_system")
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        }
                    }
                    alertDialog.dismiss()
                }
            }
            "notification" -> {
                if (localeHelper.getNotification(context)) option1.isChecked = true
                else option2.isChecked = true
                group.setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.option1 -> {
                            OneSignal.disablePush(false)
                            FirebaseMessaging.getInstance().subscribeToTopic("allUsers")
                            localeHelper.setNotification(context, true)
                        }
                        R.id.option2 -> {
                            OneSignal.disablePush(true)
//                            FirebaseMessaging.getInstance().unsubscribeFromTopic("allUsers")
                            localeHelper.setNotification(context, false)
                        }
                    }
                    alertDialog.dismiss()
                }
            }
            "azan" -> {
                if (localeHelper.getAzan(context)) option1.isChecked = true
                else option2.isChecked = true
                group.setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.option1 -> {
                            Azan().setAlarm(context)
                            localeHelper.setAzan(context, true)
                        }
                        R.id.option2 -> {
                            Azan().cancelAlarm(context)
                            localeHelper.setAzan(context, false)
                        }
                    }
                    alertDialog.dismiss()
                }
            }
        }
    }
}