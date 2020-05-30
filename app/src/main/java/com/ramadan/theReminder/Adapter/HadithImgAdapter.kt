package com.smarteist.imageslider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramadan.theReminder.Model.Hadith
import com.ramadan.theReminder.R
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.hadith_img.view.*

class HadithImgAdapter(val context: Context) :
    SliderViewAdapter<HadithImgAdapter.CustomView>() {

    private var dataList = mutableListOf<Hadith>()

    fun setDataList(data: MutableList<Hadith>) {
        dataList = data
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup): CustomView {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.hadith_img, parent, false)
        return CustomView(inflate)
    }

    override fun onBindViewHolder(viewHolder: CustomView, position: Int) {
        val hadith: Hadith = dataList[position]
        return viewHolder.customView(hadith)
    }

    override fun getCount(): Int {
        return dataList.size
    }

    class CustomView(itemView: View) :
        ViewHolder(itemView) {
        fun customView(hadith: Hadith) {
            Picasso.get()
                .load(hadith.imgUrl).error(R.drawable.error_img).placeholder(R.drawable.load_img)
                .into(itemView.hadithImg)
        }

    }

}