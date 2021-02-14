package com.ramadan.islami.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.ramadan.islami.R
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
import java.lang.reflect.Field


class Dashboard : AppCompatActivity(), Listener {
    private lateinit var mAdView: AdView
    private lateinit var mAdView1: AdView
    private lateinit var mInterstitialAd: InterstitialAd
    private val localeHelper = LocaleHelper()
    private lateinit var storiesSlider: SliderView
    private lateinit var storiesAdapter: SliderAdapter
    private lateinit var quotesSlider: SliderView
    private lateinit var quotesAdapter: SliderAdapter

    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment
    private var isEnglish: Boolean = true

    override fun onStart() {
        super.onStart()
        observeDate()
        appLanguage()
        initMenuFragment()
//        overrideDefaultFont("fonts/aref_regular.ttf", assets)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        supportActionBar?.hide()
        isEnglish = localeHelper.getDefaultLanguage(this) == "en"
        viewModel.listener = this

        val typeface = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            resources.getFont(R.font.aref_regular)
        } else {
            ResourcesCompat.getFont(this, R.font.aref_regular)
        }
        textView.typeface = typeface
        menuOptions.setOnClickListener { showContextMenuDialogFragment() }
        storiesSlider = findViewById(R.id.storiesSlider)
        storiesAdapter = SliderAdapter(this)
        storiesSlider.setSliderAdapter(storiesAdapter)
        storiesSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LOCALE
        storiesSlider.startAutoCycle()
        storiesSlider.isAutoCycle = true
        storiesSlider.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        storiesSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)

        quotesSlider = findViewById(R.id.quotesSlider)
        quotesAdapter = SliderAdapter(this)
        quotesSlider.setSliderAdapter(quotesAdapter)
        quotesSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LOCALE
        quotesSlider.startAutoCycle()
        quotesSlider.isAutoCycle = true
        quotesSlider.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        quotesSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)

        storiesCard.setOnClickListener { startActivity(Intent(this, StoryDashboard::class.java)) }
        quotesCard.setOnClickListener { startActivity(Intent(this, QuoteDashboard::class.java)) }
        familyTreeCard.setOnClickListener { startActivity(Intent(this, FamilyTree::class.java)) }
        topics.setOnClickListener { startActivity(Intent(this, Collection::class.java)) }
        muhammadTree.setOnClickListener { startActivity(Intent(this, MuhammadTree::class.java)) }
        prophetsTree.setOnClickListener { startActivity(Intent(this, ProphetsTree::class.java)) }
        bigTree.setOnClickListener { startActivity(Intent(this, BigTree::class.java)) }
//        muhammadStory.setOnClickListener { startActivity(Intent(this, BigTree::class.java)) }
//        jobStory.setOnClickListener { startActivity(Intent(this, Videos::class.java)) }

//        val testDeviceIds = listOf("33BE2250B43518CCDA7DE426D04EE231")
//        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
//        MobileAds.setRequestConfiguration(configuration)


//        mAdView1 = findViewById(R.id.adView1)
//        mAdView1.loadAd(AdRequest.Builder().build())
//
//        mInterstitialAd = InterstitialAd(this)
//        mInterstitialAd.adUnitId = getString(R.string.Interstitial_ad_unit_id)
//        mInterstitialAd.loadAd(AdRequest.Builder().build())
//        mInterstitialAd.adListener = object : AdListener() {}

    }

    private fun observeDate() {
        viewModel.fetchStories(isEnglish).observe(this, { storiesAdapter.setStoriesDataList(it) })
        viewModel.fetchQuotes(isEnglish).observe(this, { quotesAdapter.setCategoryDataList(it) })
        GlobalScope.launch(Dispatchers.IO) {
            delay(1000)
            withContext(Dispatchers.Main) {
                mAdView = findViewById(R.id.adView)
                mAdView.loadAd(AdRequest.Builder().build())
            }
        }
    }

    private fun appLanguage() {
        if (isEnglish) switchBtn.isChecked = true
        switchBtn.setOnCheckedChangeListener { button, isChecked ->
            when {
                isChecked -> localeHelper.persist(applicationContext, "en")
                else -> localeHelper.persist(applicationContext, "ar")
            }
            startActivity(Intent(this, Dashboard::class.java)).also { finish() }
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

    private fun overrideDefaultFont(
        customFontFileNameInAssets: String,
        assets: AssetManager,
    ) {
        //Load custom Font from File
        val customFontTypeface: Typeface =
            Typeface.createFromAsset(assets, customFontFileNameInAssets)
        //Get Fontface.Default Field by reflection
        val typeFaceClass: Class<*> = Class.forName("android.graphics.Typeface")
        val defaultFontTypefaceField: Field = typeFaceClass.getField("SERIF")
        defaultFontTypefaceField.isAccessible = true
        defaultFontTypefaceField.set(null, customFontTypeface)
    }

    private fun initMenuFragment() {
        val menuParams = MenuParams(
            actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
            menuObjects = getMenuObjects(),
            isClosableOutside = true,
            gravity = MenuGravity.START
        )
        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
            menuItemClickListener = { view, position ->
                when (position) {
                    0 -> startActivity(Intent(view.context, StoryDashboard::class.java))
                    1 -> startActivity(Intent(view.context, QuoteDashboard::class.java))
                    2 -> startActivity(Intent(view.context, FamilyTree::class.java))
                    3 -> startActivity(Intent(view.context, Collection::class.java))
                    4 -> alertDialog(getString(R.string.pick_one),
                        getString(R.string.light_theme),
                        getString(R.string.night_theme),
                        false)
                    5 -> alertDialog(getString(R.string.pick_one),
                        getString(R.string.arabic),
                        getString(R.string.english),
                        true)
                    6 ->
                        startActivity(Intent(view.context, StoryDashboard::class.java))
                    7 -> startActivity(Intent(view.context, VideosList::class.java))
                    8 -> startActivity(Intent(view.context, SendFeedback::class.java))
                    9 -> startActivity(Intent(view.context, PrayerTimes::class.java))
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
            setBgColorValue(primaryColor)
            add(this)
        }
        MenuObject(getString(R.string.send_feedback)).apply {
            setResourceValue(R.drawable.feedback)
            setBgColorValue(primaryColorDark)
            add(this)
        }
        MenuObject(getString(R.string.about_app)).apply {
            setResourceValue(R.drawable.info)
            setBgColorValue(primaryColor)
            add(this)
        }
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }

    private fun alertDialog(
        title: String,
        option1: String,
        option2: String,
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
        val option1 = view.findViewById<RadioButton>(R.id.option1).also { it.text = option1 }
        val option2 = view.findViewById<RadioButton>(R.id.option2).also { it.text = option2 }
        if (isLanguageSetting) {
            if (localeHelper.getDefaultLanguage(this) == "ar") option1.isChecked = true
            else option2.isChecked = true
            group.setOnCheckedChangeListener { group, checkedId ->
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
            group.setOnCheckedChangeListener { group, checkedId ->
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

    fun firstSuggestedCard(view: View) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val story = viewModel.fetchStory(isEnglish, "muhammad")
                val intent = Intent(applicationContext, Story::class.java)
                intent.putExtra("story", story)
                startActivity(intent)
            }
        }
    }

    fun secondSuggestedCard(view: View) {
        startActivity(Intent(this, Hadiths::class.java))
    }

    fun thirdSuggestedCard(view: View) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val story = viewModel.fetchStory(isEnglish, "job")
                val intent = Intent(applicationContext, Story::class.java)
                intent.putExtra("story", story)
                startActivity(intent)
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