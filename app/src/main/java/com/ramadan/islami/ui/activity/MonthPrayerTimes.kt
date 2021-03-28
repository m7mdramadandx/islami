package com.ramadan.islami.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Prayer
import com.ramadan.islami.ui.adapter.PrayTimeAdapter

class MonthPrayerTimes : AppCompatActivity() {

    private lateinit var prayer: Prayer
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PrayTimeAdapter

    override fun onResume() {
        super.onResume()
        MainActivity.firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, title.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month_prayer_times)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.table_recycler_view)
        adapter = PrayTimeAdapter()
        prayer = intent.getSerializableExtra("prayer") as Prayer
        adapter.setPrayerDataList(prayer.data.toMutableList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        val month = prayer.data.first().date.gregorian.month.en
        title = "${getString(R.string.prayerTimesTitle)} $month"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}