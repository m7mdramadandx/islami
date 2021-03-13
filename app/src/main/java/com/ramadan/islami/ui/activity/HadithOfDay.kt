package com.ramadan.islami.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.ActionMode.Callback
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.ui.viewModel.WebServiceViewModel
import com.ramadan.islami.utils.*
import kotlinx.android.synthetic.main.activity_hadith_of_day.*
import kotlin.math.max
import kotlin.math.min


class HadithOfDay : AppCompatActivity() {
    private lateinit var viewModel: WebServiceViewModel

    private val localeHelper = LocaleHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hadith_of_day)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val tafsir = 0
        hadithBody.customSelectionActionModeCallback = object : Callback {
            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu): Boolean {
                p1.removeItem(android.R.id.cut)
                p1.removeItem(android.R.id.paste)
                p1.removeItem(android.R.id.selectAll)
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    p1.removeItem(android.R.id.shareText)
                }
                return true
            }

            override fun onCreateActionMode(p0: ActionMode?, p1: Menu): Boolean {
                p1.add(1, tafsir, 1, R.string.tafsir).setIcon(R.drawable.menu)
                return true
            }

            override fun onDestroyActionMode(p0: ActionMode?) {
            }

            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
                when (p1!!.itemId) {
                    tafsir -> {
                        var min = 0
                        var max: Int = hadithBody.text.length
                        if (hadithBody.isFocused) {
                            val selStart: Int = hadithBody.selectionStart
                            val selEnd: Int = hadithBody.selectionEnd
                            min = max(0, min(selStart, selEnd))
                            max = max(0, max(selStart, selEnd))
                        }
                        val selectedText: CharSequence = hadithBody.text.subSequence(min, max)
                        val intent = Intent()
                        intent.action = Intent.ACTION_VIEW
                        intent.addCategory(Intent.CATEGORY_BROWSABLE)
                        intent.data = Uri.parse("https://translate.google.com/$selectedText")
                        startActivity(intent)
                        p0!!.finish()
                        return true
                    }
                    else -> {
                    }
                }
                return false
            }

        }
        if (localeHelper.getHadithOfDay(this).isNotEmpty()) {
            if (localeHelper.getHadithOfDay(this).contains(dateOfDay())) {
                hadithBody.visibility = View.VISIBLE
                hadithTitle.text = localeHelper.getHadithOfDay(this).elementAt(0)
                hadithBody.text = localeHelper.getHadithOfDay(this).elementAt(1)
            }
        } else {
            viewModel =
                ViewModelProvider(this,
                    ViewModelFactory(ApiHelper(RetrofitBuilder("https://api.sunnah.com/").apiService()))
                ).get(WebServiceViewModel::class.java)
            setupObservers()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupObservers() {
        viewModel.hadithOfDay().observe(this, {
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    progress.visibility = View.GONE
                    hadithBody.visibility = View.VISIBLE
                    val chapterTitle = it.data!!.hadith[1].chapterTitle
                    val hadith =
                        it.data.hadith[1].body.removeSurrounding("<p>", "</p>").removePrefix("<br>")
                    hadithTitle.text = chapterTitle
                    hadithBody.text = hadith
                    localeHelper.setHadithOfDay(this, hadith, chapterTitle)
                }
                ResponseStatus.ERROR -> {
                    progress.visibility = View.GONE
                    Log.e(debug_tag, it.message.toString())
                    showMessage(this, it.message.toString())
                }
                ResponseStatus.LOADING -> progress.visibility = View.VISIBLE
            }
        })
    }
}