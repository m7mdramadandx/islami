package com.ramadan.islami.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ActionMode
import android.view.ActionMode.Callback
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.data.model.Azkar
import com.ramadan.islami.data.model.Verse
import com.ramadan.islami.ui.viewModel.LocalViewModel
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.ui.viewModel.WebServiceViewModel
import com.ramadan.islami.utils.*
import kotlinx.android.synthetic.main.activity_quote_of_day.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.max
import kotlin.math.min


class QuoteOfDay : AppCompatActivity() {
    private lateinit var webServiceViewModel: WebServiceViewModel
    private lateinit var localViewModel: LocalViewModel
    private val localeHelper = LocaleHelper()
    private val tafseer = 0
    private var intentKey: String = ""
    private lateinit var quoteOfDayItem: Azkar.AzkarItem
    private lateinit var verseOfDayItem: Verse.VerseItem

    override fun onStart() {
        super.onStart()
        intentKey = intent.getStringExtra("intentKey")!!
        when (intentKey) {
            "verse" -> fetchVerseDay()
            "hadith" -> fetchHadith()
            "zekr" -> fetchAzkar()
        }
    }

    override fun onResume() {
        super.onResume()
        MainActivity.firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, title.toString())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_of_day)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        textBody.customSelectionActionModeCallback = object : Callback {
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
                p1.add(1, tafseer, 1, R.string.tafsir).setIcon(R.drawable.ic_menu)
                return true
            }

            override fun onDestroyActionMode(p0: ActionMode?) {
            }

            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
                when (p1!!.itemId) {
                    tafseer -> {
                        var min = 0
                        var max: Int = textBody.text.length
                        if (textBody.isFocused) {
                            val selStart: Int = textBody.selectionStart
                            val selEnd: Int = textBody.selectionEnd
                            min = max(0, min(selStart, selEnd))
                            max = max(0, max(selStart, selEnd))
                        }
                        val selectedText: CharSequence = textBody.text.subSequence(min, max)
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
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun fetchVerseDay() {
        title = getString(R.string.verseOfDay)
        if (localeHelper.getVerseOfDay(this).contains(dateOfDay() + " date")) {
            val verse = localeHelper.getVerseOfDay(this)
            textBody.visibility = View.VISIBLE
            textTitle.text = verse.find { it.contains("surah") }?.removeSuffix("surah")
            textBody.text = verse.find { it.contains("ayah") }?.removeSuffix("ayah")
        } else fetchNewVerseDay()
    }

    private fun fetchNewVerseDay() {
        localViewModel = ViewModelProvider(this).get(LocalViewModel::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Main) {
                progress.visibility = View.VISIBLE
                verseOfDayItem = localViewModel.getVerseOfDay(this@QuoteOfDay)?.random()!!.apply {
                    textBody.visibility = View.VISIBLE
                    textTitle.text = surah
                    textBody.text = ayah + "\n" + translation
                    localeHelper.setVerseOfDay(this@QuoteOfDay, this)
                }
                progress.visibility = View.GONE
            }
        }
    }

    private fun fetchHadith() {
        title = getString(R.string.hadithOfDay)
        if (localeHelper.getHadithOfDay(this).contains(dateOfDay() + " date")) {
            val zekr = localeHelper.getHadithOfDay(this)
            textBody.visibility = View.VISIBLE
            textTitle.text = zekr.find { it.contains("title") }?.removeSuffix("title")
            textBody.text = zekr.find { it.contains("body") }?.removeSuffix("body")
        } else {
            webServiceViewModel =
                ViewModelProvider(
                    this,
                    ViewModelFactory(ApiHelper(RetrofitBuilder("https://api.sunnah.com/").apiService()))
                ).get(WebServiceViewModel::class.java)
            fetchNewHadith()
        }
    }

    private fun fetchNewHadith() {
        webServiceViewModel.hadithOfDay().observe(this, {
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    progress.visibility = View.GONE
                    textBody.visibility = View.VISIBLE
                    val chapterTitle = it.data!!.hadith[1].chapterTitle
                    val hadith = it.data.hadith[1].body.removeSurrounding("<p>", "</p>")
                    textTitle.text = chapterTitle
                    textBody.text = hadith
                    localeHelper.setHadithOfDay(this, hadith, chapterTitle)
                }
                ResponseStatus.ERROR -> {
                    progress.visibility = View.GONE
                    showMessage(this, it.message.toString())
                }
                ResponseStatus.LOADING -> progress.visibility = View.VISIBLE
            }
        })
    }

    private fun fetchAzkar() {
        title = getString(R.string.azkarOfDay)
        if (localeHelper.getAzkarOfDay(this).contains(dateOfDay() + " date")) {
            val zekr = localeHelper.getAzkarOfDay(this)
            textBody.visibility = View.VISIBLE
            textTitle.text = zekr.find { it.contains("title") }?.removeSuffix("title")
            textBody.text = zekr.find { it.contains("body") }?.removeSuffix("body")
            textDescription.text =
                zekr.find { it.contains("description") }?.removeSuffix("description")
            textReference.text =
                zekr.find { it.contains("reference") }?.removeSuffix("reference")
            if (localeHelper.getAzkarOfDay1(this).contains(dateOfDay() + " date")) {
                val zekr1 = localeHelper.getAzkarOfDay1(this)
                textBody1.visibility = View.VISIBLE
                spacer.visibility = View.VISIBLE
                textTitle1.text = zekr1.find { it.contains("title") }?.removeSuffix("title")
                textBody1.text = zekr1.find { it.contains("body") }?.removeSuffix("body")
                textDescription1.text =
                    zekr1.find { it.contains("description") }?.removeSuffix("description")
            }
        } else fetchNewAzkar()
    }

    private fun fetchNewAzkar() {
        localViewModel = ViewModelProvider(this).get(LocalViewModel::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Main) {
                progress.visibility = View.VISIBLE
                quoteOfDayItem = localViewModel.getAzkar(this@QuoteOfDay)?.random()!!.apply {
                    textBody.visibility = View.VISIBLE
                    textTitle.text = category
                    textBody.text = zekr
                    textDescription.text = description
                    localeHelper.setAzkarOfDay(this@QuoteOfDay, this)
                }
                quoteOfDayItem = localViewModel.getAzkar(this@QuoteOfDay)?.random()!!.apply {
                    textBody1.visibility = View.VISIBLE
                    spacer.visibility = View.VISIBLE
                    textTitle1.text = category
                    textBody1.text = zekr
                    textDescription1.text = description
                    localeHelper.setAzkarOfDay1(this@QuoteOfDay, this)
                }
                progress.visibility = View.GONE
            }
        }
    }
}
