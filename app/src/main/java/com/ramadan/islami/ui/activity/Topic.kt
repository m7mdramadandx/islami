package com.ramadan.islami.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Topic
import com.ramadan.islami.ui.adapter.TopicAdapter
import com.ramadan.islami.ui.viewModel.ViewModel
import com.ramadan.islami.utils.LocaleHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.topic.*

class Topic : AppCompatActivity() {
    private lateinit var topic: Topic
    private lateinit var topicAdapter: TopicAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }
    private var isEnglish: Boolean = true
    private val localeHelper = LocaleHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topic)
        topic = intent.getSerializableExtra("topic") as Topic
        val collectionId: String = intent.getStringExtra("collectionId").toString()
        supportActionBar?.hide()
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
    }

    private fun observeData() {
        Picasso.get().load(topic.image).placeholder(R.drawable.failure_img)
            .error(R.drawable.error_img).into(cover)
        topicAdapter.setTopicContentDataList(topic.content as MutableMap<String, String>,topic.brief)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}