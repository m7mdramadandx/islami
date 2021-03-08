package com.ramadan.islami.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Quran
import com.ramadan.islami.ui.adapter.DoppelgangerAdapter.QuranPage
import kotlinx.android.synthetic.main.item_quran_ayah.view.*


class DoppelgangerAdapter : RecyclerView.Adapter<QuranPage>() {

    private var ayahList: MutableList<Quran.Ayah> = mutableListOf()

    fun setAyahDataList(list: MutableList<Quran.Ayah>) {
        this.ayahList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranPage {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quran_ayah, parent, false)
        return QuranPage(view);
    }

    override fun getItemCount(): Int {
        return ayahList.last().page - ayahList.first().page + 1
    }

    override
    fun onBindViewHolder(holder: QuranPage, position: Int) {
        holder.setAyah(ayahList, ayahList.first().page + position)
    }

    class QuranPage(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setAyah(ayahList: MutableList<Quran.Ayah>, position: Int) {
            var text = String()
            itemView.apply {
                ayahList.forEach {
                    if (it.page == position) {
                        text += it.text + " (${it.numberInSurah}) "
                    }
                }
                ayahText.text = text

//                ayahNumber.text = ayah.numberInSurah.toString()
//                if (ayah.sajda != false) {
//                    ayah_sajda.visibility = View.VISIBLE
//                }
            }
        }
    }

}
