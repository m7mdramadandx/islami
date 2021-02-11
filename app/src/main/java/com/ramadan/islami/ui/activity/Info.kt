package com.ramadan.islami.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Information
import com.ramadan.islami.ui.adapter.InfoAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.information.*

class Info : AppCompatActivity() {
    private lateinit var info: Information
    private lateinit var infoAdapter: InfoAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.information)

        info = intent.getSerializableExtra("info") as Information
        supportActionBar?.hide()
        recyclerView = findViewById(R.id.contentRecyclerView)
        infoAdapter = InfoAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = infoAdapter
        observeData()
    }

    private fun observeData() {
        Picasso.get().load(info.image).placeholder(R.drawable.failure_img)
            .error(R.drawable.error_img).into(cover)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            cover.tooltipText = info.brief
        }
        infoAdapter.setInfoContentDataList(info.content as MutableMap<String, String>)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}