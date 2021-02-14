package com.ramadan.islami.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Topic
import com.ramadan.islami.ui.adapter.TopicAdapter
import com.ramadan.islami.ui.viewModel.ViewModel
import com.ramadan.islami.utils.LocaleHelper
import kotlinx.android.synthetic.main.topic.*
import kotlinx.android.synthetic.main.xx.*


class Topic : AppCompatActivity() {
    private lateinit var topic: Topic
    private lateinit var topicAdapter: TopicAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }
    private var isEnglish: Boolean = true
    private val localeHelper = LocaleHelper()
    val tag = "TOTO"
    var appBarLayout: AppBarLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topic)
        topic = intent.getSerializableExtra("topic") as Topic
        val collectionId: String = intent.getStringExtra("collectionId").toString()
        appBarLayout = findViewById(R.id.app_bar)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = topic.title
        toolbar.titleMarginStart = 80
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        isEnglish = localeHelper.getDefaultLanguage(this) == "en"
        recyclerView = findViewById(R.id.contentRecyclerView)
        topicAdapter = TopicAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = topicAdapter
        observeData()
        good.setOnCheckedChangeListener { button, isChecked ->
            viewModel.rateTopic(isEnglish, collectionId, topic.id, isChecked)
        }
        bad.setOnCheckedChangeListener { button, isChecked ->
            viewModel.rateTopic(isEnglish, collectionId, topic.id, !isChecked)
        }
        toolbar_layout.setContentScrimColor(resources.getColor(R.color.colorPrimary))
        toolbar_layout.setCollapsedTitleTextColor(Color.WHITE)
        toolbar_layout.setBackgroundColor(resources.getColor(R.color.silver_grey))
        toolbar_layout.setExpandedTitleColor(resources.getColor(R.color.colorPrimary))


    }

    private fun observeData() {
//        Picasso.get().load(topic.image).placeholder(R.drawable.failure_img)
//            .error(R.drawable.error_img).into(cover)
        topicAdapter.setTopicContentDataList(topic.content as MutableMap<String, String>,
            topic.brief)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}