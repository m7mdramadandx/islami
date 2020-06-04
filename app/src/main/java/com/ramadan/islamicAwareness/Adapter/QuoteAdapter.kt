package com.ramadan.islamicAwareness.Adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islamicAwareness.Model.Category
import com.ramadan.islamicAwareness.R
import com.ramadan.islamicAwareness.sampledata.Quote
import com.ramadan.islamicAwareness.sampledata.QuoteDashboard
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.quote_item.view.*


class QuoteAdapter(val context: QuoteDashboard) :
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
            if (category.name == "Death") {
                itemView.categoryName.text = "Judgement Day"
            } else {
                itemView.categoryName.text = category.name
            }
            itemView.setOnClickListener(View.OnClickListener {
                val intent = Intent(itemView.context, Quote::class.java)
                val bundle = Bundle()
                bundle.putString("category", category.name)
                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
            })
        }
    }
}