package com.ramadan.islami.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.ramadan.islami.R
import kotlinx.android.synthetic.main.activity_ayah.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_ayah.*
import kotlinx.android.synthetic.main.fragment_ayah.doppelgangerViewPager

/**
 * Main Screen
 */
class AyahActivity : AppCompatActivity() {

    private lateinit var doppelgangerNamesArray: Array<String>

    private var doppelgangerPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Toast.makeText(this@AyahActivity,
                "Selected position: $position",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch to AppTheme for displaying the activity
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ayah)

        doppelgangerNamesArray = resources.getStringArray(R.array.custom_months)

        //TODO:3 Wire DoppelgangerAdapter with ViewPager2 here
//        val doppelgangerAdapter = DoppelgangerAdapter(this, doppelgangerNamesArray.size)
//        doppelgangerViewPager.adapter = doppelgangerAdapter

        //TODO:5 Register page change callback here
//        doppelgangerViewPager.registerOnPageChangeCallback(doppelgangerPageChangeCallback)

        //TODO:7 Change ViewPager2 orientation here
//        doppelgangerViewPager.orientation = ORIENTATION_VERTICAL

        //TODO:10 Connect TabLayout and ViewPager2 here
//        TabLayoutMediator(tabLayout, doppelgangerViewPager) { tab, position ->
            //To get the first name of doppelganger celebrities
//            tab.text = doppelgangerNamesArray[position].substringBefore(' ')
//        }.attach()

        //TODO:11 Force to RTL mode
//        doppelgangerViewPager.layoutDirection = ViewPager2.LAYOUT_DIRECTION_RTL
//        tabLayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }

    override fun onDestroy() {
        super.onDestroy()
        doppelgangerViewPager.unregisterOnPageChangeCallback(doppelgangerPageChangeCallback)
    }
}
