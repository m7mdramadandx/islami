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
import com.ramadan.islami.R
import com.ramadan.islami.ui.activity.Qibla
import com.ramadan.islami.utils.LocaleHelper

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
        val root = inflater.inflate(R.layout.settings, container, false)
        val language = root.findViewById<CardView>(R.id.language)
        language.setOnClickListener {
            alertDialog("Language setting",
                getString(R.string.arabic),
                getString(R.string.english),
                true)
        }
        val theme = root.findViewById<CardView>(R.id.theme)
        theme.setOnClickListener {
            alertDialog("Theme setting",
                getString(R.string.lightTheme),
                getString(R.string.nightTheme),
                false)
        }

        return root
    }


    private fun alertDialog(
        title: String,
        optionOne: String,
        optionTwo: String,
        isLanguageSetting: Boolean,
    ) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val view = View.inflate(requireContext(), R.layout.alert_dialog, null)
        dialogBuilder.setView(view)
        val alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        alertDialog.setCancelable(true)
        view.findViewById<TextView>(R.id.title).text = title
        val group = view.findViewById<RadioGroup>(R.id.group)
        val option1 = view.findViewById<RadioButton>(R.id.option1).also { it.text = optionOne }
        val option2 = view.findViewById<RadioButton>(R.id.option2).also { it.text = optionTwo }
        if (isLanguageSetting) {
            if (localeHelper.getDefaultLanguage(requireContext()) == "ar") option1.isChecked = true
            else option2.isChecked = true
            group.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.option1 -> localeHelper.persist(requireContext(), "ar")
                    R.id.option2 -> localeHelper.persist(requireContext(), "en")
                }
                startActivity(Intent(requireContext(),
                    Qibla::class.java)).also { super.onDestroy() }
            }
        } else {
            val option3 =
                view.findViewById<RadioButton>(R.id.option3)
                    .also { it.visibility = View.VISIBLE }
            when (localeHelper.getDefaultTheme(requireContext())) {
                "light" -> option1.isChecked = true
                "night" -> option2.isChecked = true
                else -> option3.isChecked = true
            }
            group.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.option1 -> {
                        localeHelper.setTheme(requireContext(), "light")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    R.id.option2 -> {
                        localeHelper.setTheme(requireContext(), "night")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    R.id.option3 -> {
                        localeHelper.setTheme(requireContext(), "follow_system")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                }
            }
        }
    }


}