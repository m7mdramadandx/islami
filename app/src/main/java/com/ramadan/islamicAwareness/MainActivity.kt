@file:Suppress("DEPRECATION")

package com.ramadan.islamicAwareness

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.*
import com.google.android.material.tabs.TabLayout
import com.ramadan.islamicAwareness.ui.activity.ProphetsTree
import com.ramadan.islamicAwareness.ui.activity.QuoteDashboard
import com.ramadan.islamicAwareness.ui.activity.StoryDashboard
import com.ramadan.islamicAwareness.ui.adapter.ViewPagerAdapter
import com.ramadan.islamicAwareness.utils.LocaleHelper


class MainActivity : AppCompatActivity() {
    private val quoteDashboard: QuoteDashboard = QuoteDashboard()
    private val storyDashboard: StoryDashboard = StoryDashboard()
    private val prophetsTree: ProphetsTree = ProphetsTree()
    private lateinit var mAdView: AdView
    private lateinit var mInterstitialAd: InterstitialAd
    private val localeHelper = LocaleHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val tabLayout: TabLayout = findViewById(R.id.tabs)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, 0)
        viewPagerAdapter.addFragment(storyDashboard, getString(R.string.stories))
        viewPagerAdapter.addFragment(quoteDashboard, getString(R.string.quotes))
        viewPagerAdapter.addFragment(prophetsTree, getString(R.string.prophets_tree))
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.story)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.quote)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.googleg_disabled_color_18)


        MobileAds.initialize(this, getString(R.string.ad_id))
//        val testDeviceIds = listOf("33BE2250B43518CCDA7DE426D04EE231")
//        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
//        MobileAds.setRequestConfiguration(configuration)

        mAdView = findViewById(R.id.adView)
        mAdView.loadAd(AdRequest.Builder().build())

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.Interstitial_ad_unit_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.language_menu, menu)
        val menuItem = menu?.findItem(R.id.switchItem)
        val switch = menuItem!!.actionView as SwitchCompat
        if (localeHelper.getDefaultLanguage(this) == "en") switch.isChecked = true
        switch.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                localeHelper.persist(applicationContext, "en")
            } else {
                localeHelper.persist(applicationContext, "ar")
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(localeHelper.setLocale(base!!,
            localeHelper.getDefaultLanguage(base)))
    }

}