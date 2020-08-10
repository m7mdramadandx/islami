package com.ramadan.islamicAwareness.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramadan.islamicAwareness.R
import com.ramadan.islamicAwareness.data.model.Quote
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.quote_img.view.*


class QuoteImgAdapter(val context: Context) :
    SliderViewAdapter<QuoteImgAdapter.CustomView>() {

    private var dataList = mutableListOf<Quote>()

    fun setDataList(data: MutableList<Quote>) {
        dataList = data
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup): CustomView {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.quote_img, parent, false)
        return CustomView(inflate)
    }

    override fun onBindViewHolder(viewHolder: CustomView, position: Int) {
        val quote: Quote = dataList[position]
        return viewHolder.customView(quote)
    }

    override fun getCount(): Int {
        return dataList.size
    }

    class CustomView(itemView: View) :
        ViewHolder(itemView) {
        val _quote = com.ramadan.islamicAwareness.ui.activity.Quote()
        fun customView(quote: Quote) {
            Picasso.get()
                .load(quote.imgUrl).error(R.drawable.error_img).placeholder(R.drawable.load_img)
                .into(itemView.hadithImg)
            itemView.setOnClickListener(View.OnClickListener {
                _quote.x(quote.imgUrl!!, itemView.context)
            })
        }
    }

}