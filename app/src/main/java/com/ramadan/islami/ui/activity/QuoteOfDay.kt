package com.ramadan.islami.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.data.model.Verse
import com.ramadan.islami.ui.viewModel.LocalViewModel
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.ui.viewModel.WebServiceViewModel
import com.ramadan.islami.utils.*
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuGravity
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import kotlinx.android.synthetic.main.activity_quote_of_day.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class QuoteOfDay : AppCompatActivity() {
    private lateinit var webServiceViewModel: WebServiceViewModel
    private lateinit var localViewModel: LocalViewModel
    private val localeHelper = LocaleHelper()
    private var intentKey: String = ""
    private lateinit var verseOfDayItem: Verse.VerseItem
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment
    private lateinit var textBody: TextView

    override fun onStart() {
        super.onStart()
        intentKey = intent.getStringExtra("intentKey")!!
        when (intentKey) {
            "verse" -> fetchVerseDay()
            "hadith" -> fetchHadith()
        }
        initMenuFragment()
    }

    override fun onResume() {
        super.onResume()
        MainActivity.firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, title.toString())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_of_day)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        textBody = findViewById(R.id.textContent)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.let { if (it.itemId == R.id.context_menu) showContextMenuDialogFragment() }
        return super.onOptionsItemSelected(item)
    }

    private fun initMenuFragment() {
        val menuParams = MenuParams(
            actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
            menuObjects = getMenuObjects(),
            isClosableOutside = true,
            gravity = MenuGravity.END
        )
        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
            menuItemClickListener = { view, position ->
                when (position) {
                    0 -> Intent(Intent.ACTION_SEND).also {
                        it.type = "text/plain"
                        it.putExtra(Intent.EXTRA_TEXT, textBody.text.toString())
                        startActivity(it)
                    }
                }
            }
        }
    }

    private fun getMenuObjects() = mutableListOf<MenuObject>().apply {
        MenuObject(getString(R.string.share)).apply {
            setResourceValue(R.drawable.ic_share)
            setBgColorValue((Color.rgb(22, 36, 71)))
            add(this)
        }
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager,
                ContextMenuDialogFragment.TAG)
        }
    }

}
