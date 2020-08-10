package com.ramadan.islamicAwareness

import android.os.Bundle
import android.view.Menu
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.night_mode, menu)
        val item = menu.findItem(R.id.switchOnOffItem)
        item.setActionView(R.layout.switch_theme)
        val switchTheme: Switch = item.actionView.findViewById(R.id.switchOnOff)
        switchTheme.setOnCheckedChangeListener { p0, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Toast.makeText(application, "Night theme ON", Toast.LENGTH_SHORT)
                    .show()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Toast.makeText(application, "Day theme ON", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return true
    }
}