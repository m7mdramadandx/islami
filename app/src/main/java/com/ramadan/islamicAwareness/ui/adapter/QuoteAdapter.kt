package com.ramadan.islamicAwareness.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islamicAwareness.R
import com.ramadan.islamicAwareness.data.model.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.quote_item.view.*
import com.ramadan.islamicAwareness.ui.activity.Quote as QuoteActivity


class QuoteAdapter(val context: Context) :
    RecyclerView.Adapter<QuoteAdapter.CustomView>() {
    private var dataList = mutableListOf<Category>()

    fun setDataList(data: MutableList<Category>) {
        dataList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.quote_item, parent, false)
        return CustomView(view)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0) {
            dataList.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        val category: Category = dataList[position]
        holder.customView(category)
    }


    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun customView(category: Category) {
            Picasso.get().load(category.imgUrl).error(R.drawable.error_img)
                .placeholder(R.drawable.load_img).into(itemView.categoryImg)
            if (category.name == "Death")
                itemView.categoryName.text = "Judgement Day"
            else
                itemView.categoryName.text = category.name
            itemView.setOnClickListener {
                Intent(itemView.context, QuoteActivity::class.java).apply {
                    putExtra("category", category.name)
                    println(category.name + " +++")
                    itemView.context.startActivity(this)
                }
            }
        }
    }
}