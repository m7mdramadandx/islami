package com.ramadan.islami.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.RecycleViewAdapter
import com.ramadan.islami.ui.adapter.SliderAdapter
import com.ramadan.islami.ui.viewModel.Listener
import com.ramadan.islami.ui.viewModel.ViewModel
import com.ramadan.islami.utils.LocaleHelper
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuGravity
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import kotlinx.android.synthetic.main.dashboard.*
import kotlinx.coroutines.*


class Dashboard : AppCompatActivity(), Listener {
    private val viewModel by lazy { ViewModelProvider(this).get(ViewModel::class.java) }
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment
    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var mAdView: AdView
    private lateinit var suggestionRCV: RecyclerView
    private lateinit var storiesSlider: SliderView
    private lateinit var quotesSlider: SliderView
    private lateinit var recycleViewAdapter: RecycleViewAdapter
    private val storiesAdapter = SliderAdapter()
    private val quotesAdapter = SliderAdapter()
    private val localeHelper = LocaleHelper()
    private var isEnglish: Boolean = true
    private val appURL = ""

    override fun onStart() {
        super.onStart()
        isEnglish = localeHelper.getDefaultLanguage(this) == "en"
        observeDate()
        initMenuFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { showContextMenuDialogFragment() }
        viewModel.listener = this
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LOCALE
        recycleViewAdapter = RecycleViewAdapter(true)
        suggestionRCV = findViewById(R.id.suggestionRecyclerView)
        suggestionRCV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        suggestionRCV.adapter = recycleViewAdapter
        storiesSlider = findViewById(R.id.storiesSlider)
        storiesSlider.setSliderAdapter(storiesAdapter)
        storiesSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_RTL
        storiesSlider.startAutoCycle()
        storiesSlider.isAutoCycle = true
        storiesSlider.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        storiesSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)

        quotesSlider = findViewById(R.id.quotesSlider)
        quotesSlider.setSliderAdapter(quotesAdapter)
        quotesSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_RTL
        quotesSlider.startAutoCycle()
        quotesSlider.isAutoCycle = true
        quotesSlider.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        quotesSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)

        storiesCard.setOnClickListener { startActivity(Intent(this, PrayerTimes::class.java))}
//        storiesCard.setOnClickListener { startActivity(Intent(this, StoryDashboard::class.java)) }
        quotesCard.setOnClickListener { startActivity(Intent(this, HadithOfDay::class.java)) }
//        quotesCard.setOnClickListener { startActivity(Intent(this, QuoteDashboard::class.java)) }
        familyTreeCard.setOnClickListener { startActivity(Intent(this, FamilyTree::class.java)) }
        topics.setOnClickListener { startActivity(Intent(this, Collection::class.java)) }
        muhammadTree.setOnClickListener { startActivity(Intent(this, MuhammadTree::class.java)) }
        prophetsTree.setOnClickListener { startActivity(Intent(this, ProphetsTree::class.java)) }
        bigTree.setOnClickListener { startActivity(Intent(this, BigTree::class.java)) }
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("TOTO", adError.message)
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
//                    mInterstitialAd.show(this@Dashboard)
                }
            })

    }

    private fun observeDate() {
        viewModel.fetchSuggestion(isEnglish)
            .observe(this, { recycleViewAdapter.suggestionDataList(it) })
        viewModel.fetchStories(isEnglish).observe(this, { storiesAdapter.setStoriesDataList(it) })
        viewModel.fetchQuotes(isEnglish).observe(this, { quotesAdapter.setCategoryDataList(it) })
        GlobalScope.launch(Dispatchers.IO) {
            delay(1000)
            withContext(Dispatchers.Main) {
                mAdView = findViewById(R.id.adView)
//                mAdView.loadAd(AdRequest.Builder().build())

            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(localeHelper.setLocale(base!!,
            localeHelper.getDefaultLanguage(base)))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeHelper.setLocale(this, localeHelper.getDefaultLanguage(this))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }

    private fun initMenuFragment() {
        val menuParams = MenuParams(
            actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
            menuObjects = getMenuObjects(),
            isClosableOutside = true,
            gravity = MenuGravity.START,
        )
        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
            menuItemClickListener = { view, position ->
                when (position) {
                    0 -> startActivity(Intent(view.context, StoryDashboard::class.java))
                    1 -> startActivity(Intent(view.context, QuoteDashboard::class.java))
                    2 -> startActivity(Intent(view.context, FamilyTree::class.java))
                    3 -> startActivity(Intent(view.context, Collection::class.java))
                    4 -> {
                        alertDialog("Theme setting",
                            getString(R.string.light_theme),
                            getString(R.string.night_theme),
                            false)
                    }
                    5 -> {
                        alertDialog("Language setting",
                            getString(R.string.arabic),
                            getString(R.string.english),
                            true)
                    }
                    6 -> startActivity(Intent(view.context, StoryDashboard::class.java))
                    7 -> {
                        Intent(Intent.ACTION_SEND).also {
                            it.type = "text/plain"
                            it.putExtra(Intent.EXTRA_TEXT,
                                "I suggest this app for you : ${appURL}")
                            startActivity(it)
                        }
                    }
                    8 -> startActivity(Intent(view.context, SendFeedback::class.java))
                    9 -> startActivity(Intent(view.context, HadithOfDay::class.java))
                }
            }
        }
    }

    private fun getMenuObjects() = mutableListOf<MenuObject>().apply {
        val primaryColor = Color.rgb(22, 36, 71)
        val primaryColorDark = Color.rgb(23, 34, 59)
        MenuObject(getString(R.string.stories)).apply {
            setResourceValue(R.drawable.story)
            setBgColorValue(primaryColor)
            add(this)
        }
        MenuObject(getString(R.string.quotes)).apply {
            setResourceValue(R.drawable.quote)
            setBgColorValue(primaryColorDark)
            add(this)
        }
        MenuObject(getString(R.string.prophets_tree)).apply {
            setResourceValue(R.drawable.family_tree_img)
            setBgColorValue(primaryColor)
            add(this)
        }
        MenuObject(getString(R.string.topics)).apply {
            setResourceValue(R.drawable.topics)
            setBgColorValue(primaryColorDark)
            add(this)
        }
        MenuObject(getString(R.string.themes)).apply {
            setResourceValue(R.drawable.theme)
            setBgColorValue(primaryColor)
            add(this)
        }
        MenuObject(getString(R.string.language)).apply {
            setResourceValue(R.drawable.language)
            setBgColorValue(primaryColorDark)
            add(this)
        }
        MenuObject(getString(R.string.rate_app)).apply {
            setResourceValue(R.drawable.rating)
            setBgColorValue(primaryColor)
            add(this)
        }
        MenuObject(getString(R.string.share)).apply {
            setResourceValue(R.drawable.share)
            setBgColorValue(primaryColorDark)
            add(this)
        }
        MenuObject(getString(R.string.send_feedback)).apply {
            setResourceValue(R.drawable.feedback)
            setBgColorValue(primaryColor)
            add(this)
        }
        MenuObject(getString(R.string.about_app)).apply {
            setResourceValue(R.drawable.info)
            setBgColorValue(primaryColorDark)
            add(this)
        }
    }


    private fun alertDialog(
        title: String,
        optionOne: String,
        optionTwo: String,
        isLanguageSetting: Boolean,
    ) {
        val dialogBuilder = AlertDialog.Builder(this)
        val view = View.inflate(this, R.layout.alert_dialog, null)
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
            if (localeHelper.getDefaultLanguage(this) == "ar") option1.isChecked = true
            else option2.isChecked = true
            group.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.option1 -> localeHelper.persist(this, "ar")
                    R.id.option2 -> localeHelper.persist(this, "en")
                }
                startActivity(Intent(this, Dashboard::class.java)).also { finish() }
            }
        } else {
            val option3 =
                view.findViewById<RadioButton>(R.id.option3).also { it.visibility = View.VISIBLE }
            when (localeHelper.getDefaultTheme(this)) {
                "light" -> option1.isChecked = true
                "night" -> option2.isChecked = true
                else -> option3.isChecked = true
            }
            group.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.option1 -> {
                        localeHelper.setTheme(this, "light")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    R.id.option2 -> {
                        localeHelper.setTheme(this, "night")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    R.id.option3 -> {
                        localeHelper.setTheme(this, "follow_system")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                }
            }
        }
    }

    override fun onStarted() {}

    override fun onSuccess() {
        progress.visibility = View.GONE
        progress1.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
        progress1.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}