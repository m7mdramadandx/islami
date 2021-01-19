package com.ramadan.islamicAwareness.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islamicAwareness.R
import com.ramadan.islamicAwareness.data.model.Prophet
import com.ramadan.islamicAwareness.ui.activity.Story
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.prophet_item.view.*


class StoryAdapter(val context: Context, val isDashboard: Boolean) :
    RecyclerView.Adapter<StoryAdapter.CustomView>() {
    private var dataList = mutableListOf<Prophet>()

    fun setDataList(data: MutableList<Prophet>) {
        dataList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.prophet_item, parent, false)
        return CustomView(view)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0) {
//            if (isDashboard) 6
//            else
            dataList.size
        } else 0
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        val prophet: Prophet = dataList[position]
        holder.customView(prophet)
    }


    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun customView(prophet: Prophet) {
            Picasso.get().load(prophet.imgUrl).error(R.drawable.error_img)
                .placeholder(R.drawable.load_img).into(itemView.prophetImg)
            if (isDashboard) {
                itemView.prophetImg.maxHeight =
                    itemView.resources.getDimension(R.dimen.max_card_height).toInt()
            }
            itemView.prophetName.text = prophet.name
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, Story::class.java)
                val bundle = Bundle()
                bundle.putString("prophetName", prophet.name)
                bundle.putStringArrayList("text", prophet.text)
                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
            }

        }
    }
}