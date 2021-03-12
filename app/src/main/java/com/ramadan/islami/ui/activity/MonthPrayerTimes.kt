package com.ramadan.islami.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Prayer
import com.ramadan.islami.ui.adapter.TableAdapter

class MonthPrayerTimes : AppCompatActivity() {

    private lateinit var prayer: Prayer
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TableAdapter

    override fun onStart() {
        super.onStart()
        prayer = intent.getSerializableExtra("prayer") as Prayer
        adapter.setPrayerDataList(prayer.data.toMutableList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)
        recyclerView = findViewById(R.id.global_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

}