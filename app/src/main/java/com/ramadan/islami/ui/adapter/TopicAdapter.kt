package com.ramadan.islami.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Azkar
import com.ramadan.islami.ui.activity.TopicDetails
import com.ramadan.islami.utils.showBrief
import kotlinx.android.synthetic.main.item_content.view.*
import kotlinx.android.synthetic.main.item_tile.view.*
import com.ramadan.islami.data.model.Topic as TopicModel


class TopicAdapter : RecyclerView.Adapter<TopicAdapter.CustomView>() {
    private var topicList = mutableListOf<TopicModel>()
    private var azkarList = mutableListOf<Azkar.AzkarItem>()
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

    fun setAzkarDataList(data: MutableList<Azkar.AzkarItem>) {
        azkarList = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        return if (topicList.size > 0) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tile, parent, false)
            CustomView(view)
        } else {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_content, parent, false)
            CustomView(view)
        }
    }


    override fun onBindViewHolder(holder: CustomView, position: Int) {
        when {
            topicList.size > 0 -> holder.topicView(topicList[position])
            azkarList.size > 0 -> holder.azkarView(azkarList[position])
            contentMap.isNotEmpty() -> holder.topicContentView(contentMap.toList()[position])
        }
    }

    override fun getItemCount(): Int {
        return when {
            topicList.size > 0 -> topicList.size
            azkarList.size > 0 -> azkarList.size
            contentMap.isNotEmpty() -> contentMap.size
            else -> 0
        }
    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun topicView(topic: TopicModel) {
            itemView.tileTitle.text = topic.title
            itemView.setOnClickListener {
                Intent(itemView.context, TopicDetails::class.java).apply {
                    putExtra("intentKey", "topic")
                    putExtra("topic", topic)
                    putExtra("collectionId", collectionId)
                    itemView.context.startActivity(this)
                }
            }
        }

        fun topicContentView(map: Pair<String, String>) {
            val regex = "^[0-9]*".toRegex()
            val match = regex.find(map.first)
            itemView.subtitle.text = map.first.removePrefix(match!!.value)
            itemView.content.text = map.second
            if (map.first.length < 3) itemView.subtitle.visibility = View.GONE
        }

        fun azkarView(azkarItem: Azkar.AzkarItem) {
            if (azkarItem.category != "أذكار الصباح" && azkarItem.category != "أذكار المساء") {
                itemView.subtitle.text = azkarItem.category
                itemView.content.text = azkarItem.zekr
            } else {
                itemView.content.text = azkarItem.zekr
                itemView.subtitle.visibility = View.GONE
            }
            itemView.content.setOnLongClickListener {
                showBrief(itemView.context.resources.getString(R.string.tafsir),
                    azkarItem.description,
                    itemView.context)
                false
            }
        }
    }
}