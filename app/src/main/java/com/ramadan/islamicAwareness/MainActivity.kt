package com.ramadan.islamicAwareness

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ramadan.islamicAwareness.ui.activity.QuoteDashboard
import com.ramadan.islamicAwareness.ui.activity.StoryDashboard
import com.ramadan.islamicAwareness.ui.adapter.ViewPagerAdapter


class MainActivity : AppCompatActivity() {
    private val quoteDashboard: QuoteDashboard =
        QuoteDashboard()
    private val storyDashboard: StoryDashboard =
        StoryDashboard()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val tabLayout: TabLayout = findViewById(R.id.tabs)
        val viewPagerAdapter =
            ViewPagerAdapter(supportFragmentManager, 0)
        viewPagerAdapter.addFragment(storyDashboard, "Stories")
        viewPagerAdapter.addFragment(quoteDashboard, "Quotes")
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.story)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.quote)

    }

}