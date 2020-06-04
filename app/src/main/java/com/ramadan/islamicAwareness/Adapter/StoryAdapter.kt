package com.ramadan.islamicAwareness.Adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islamicAwareness.Model.Prophet
import com.ramadan.islamicAwareness.R
import com.ramadan.islamicAwareness.sampledata.Story
import com.ramadan.islamicAwareness.sampledata.StoryDashboard
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.prophet_item.view.*


class StoryAdapter(val context: StoryDashboard) :
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
        if (dataList.size > 0) {
            return dataList.size
        } else {
            return 0

        }
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        val prophet: Prophet = dataList[position]
        holder.customView(prophet)
    }


    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val story = Story()
        fun customView(prophet: Prophet) {
            Picasso.get().load(prophet.imgUrl).error(R.drawable.error_img)
                .placeholder(R.drawable.load_img).into(itemView.prophetImg)
            itemView.prophetName.text = prophet.name
            itemView.setOnClickListener(View.OnClickListener {
                val intent = Intent(itemView.context, Story::class.java)
                val bundle = Bundle()
                bundle.putString("prophetName", prophet.name)
                bundle.putString("section1", prophet.section1)
                bundle.putString("section2", prophet.section2)
                bundle.putString("section3", prophet.section3)
                bundle.putString("section4", prophet.section4)
                bundle.putString("section5", prophet.section5)
                bundle.putString("section6", prophet.section6)
                bundle.putString("section7", prophet.section7)
                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
            })

        }
    }
}