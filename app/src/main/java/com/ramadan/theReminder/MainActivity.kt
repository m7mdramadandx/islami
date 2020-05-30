package com.ramadan.theReminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ramadan.theReminder.Adapter.ViewPagerAdapter

class MainActivity : AppCompatActivity() {
    private val hadithDashboard: HadithDashboard = HadithDashboard()
    private val storyDashboard: StoryDashboard = StoryDashboard()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val tabLayout: TabLayout = findViewById(R.id.tabs)
        val viewPagerAdapter =
            ViewPagerAdapter(supportFragmentManager, 0)
        viewPagerAdapter.addFragment(storyDashboard, "Stories")
        viewPagerAdapter.addFragment(hadithDashboard, "Hadiths")
        viewPager.adapter = viewPagerAdapter
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)!!.setIcon(R.drawable.story)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.hadith)

//        val badgeDrawable: BadgeDrawable = tabLayout.getTabAt(0)!!.orCreateBadge
//        badgeDrawable.isVisible = true
//        badgeDrawable.number = 12

    }
}