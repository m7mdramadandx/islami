package com.ramadan.islami.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.ui.activity.Topic
import kotlinx.android.synthetic.main.content_item.view.*
import kotlinx.android.synthetic.main.tile_item.view.*
import com.ramadan.islami.data.model.Topic as TopicModel


class TopicAdapter() :
    RecyclerView.Adapter<TopicAdapter.CustomView>() {
    private var topicList = mutableListOf<TopicModel>()
    private var contentMap: MutableMap<String, String> = mutableMapOf()
    var collectionId = String()
    var brief = String()

    fun setTopicDataList(data: MutableList<TopicModel>, collectionId: String) {
        topicList = data
        this.collectionId = collectionId
        notifyDataSetChanged()
    }

    fun setTopicContentDataList(data: MutableMap<String, String>, brief: String) {
        contentMap = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        return if (topicList.size > 0) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.tile_item, parent, false)
            CustomView(view)
        } else {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.content_item, parent, false)
            CustomView(view)
        }
    }

    override fun getItemCount(): Int {
        return when {
            topicList.size > 0 -> topicList.size
            contentMap.isNotEmpty() -> contentMap.size
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        when {
            topicList.size > 0 -> holder.topicView(topicList[position])
            contentMap.isNotEmpty() -> holder.topicContentView(contentMap.toList()[position])
        }
    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun topicView(topic: TopicModel) {
            itemView.tileTitle.text = topic.title
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, Topic::class.java)
                intent.putExtra("topic", topic)
                intent.putExtra("collectionId", collectionId)
                itemView.context.startActivity(intent)
            }
        }

        fun topicContentView(map: Pair<String, String>) {
            val regex = "^[0-9]*".toRegex()
            val match = regex.find(map.first)
            itemView.subtitle.text = map.first.removePrefix(match!!.value)
            itemView.content.text = map.second
        }

    }
}