package com.ramadan.islami.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Information
import com.ramadan.islami.ui.activity.Info
import kotlinx.android.synthetic.main.content_item.view.*
import kotlinx.android.synthetic.main.tile_item.view.*


class InfoAdapter() :
    RecyclerView.Adapter<InfoAdapter.CustomView>() {
    private var infoList = mutableListOf<Information>()
    private var contentMap: MutableMap<String, String> = mutableMapOf()


    fun setInfoDataList(data: MutableList<Information>) {
        infoList = data
        notifyDataSetChanged()
    }

    fun setInfoContentDataList(data: MutableMap<String, String>) {
        contentMap = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        return if (infoList.size > 0) {
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
            infoList.size > 0 -> infoList.size
            contentMap.isNotEmpty() -> contentMap.size
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        when {
            infoList.size > 0 -> holder.infoView(infoList[position])
            contentMap.isNotEmpty() -> holder.infoContentView(contentMap.toList()[position])
        }
    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun infoView(info: Information) {
            itemView.tileTitle.text = info.title
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, Info::class.java)
                intent.putExtra("info", info)
                itemView.context.startActivity(intent)
            }
        }

        fun infoContentView(map: Pair<String, String>) {
            val regex = "[0-9]".toRegex()
            val match = regex.find(map.first)
            itemView.subtitle.text = map.first.removePrefix(match!!.value)
            itemView.content.text = map.second
        }

    }
}