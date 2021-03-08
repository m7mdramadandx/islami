package com.ramadan.islami.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.listener.SurahListener
import com.ramadan.islami.data.model.Quran
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.utils.nf
import kotlinx.android.synthetic.main.item_surah.view.*
import java.lang.String.valueOf

class QuranAdapter : RecyclerView.Adapter<QuranAdapter.CustomView>() {
    var listener: SurahListener? = null
    private var surahList: MutableList<Surah> = mutableListOf()
    private var ayahList: MutableList<Quran.Ayah> = mutableListOf()

    fun setSuraDataList(list: MutableList<Surah>, listener: SurahListener) {
        this.listener = listener
        this.surahList = list
        notifyDataSetChanged()
    }

    fun setAyahDataList(list: MutableList<Quran.Ayah>) {
        this.ayahList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): QuranAdapter.CustomView {
        val view: View = when {
            surahList.size > 1 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_surah, parent, false)
            ayahList.size > 1 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ayah, parent, false)
            else -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ayah, parent, false)
        }
        return CustomView(view)
    }

    override fun getItemCount(): Int {
        return if (surahList.size > 0) surahList.size else ayahList.size
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        return holder.surahView(surahList[position])
//        else holder.ayahView(ayahList[position])
    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun surahView(surah: Surah) {
            itemView.apply {
                surahNumber.text = surah.number.toString()
                surahName.text = surah.name
                versesNumber.text =
                    context.getString(R.string.versesNumber) + valueOf(nf.format(surah.ayahs.size))
                juzNumber.text =
                    context.getString(R.string.juzNumber) + valueOf(nf.format(surah.ayahs.first().juz))
                revelationType.text = surah.revelationType
                if (surah.ayahs.first().juz % 2 == 0) {
                    surahCard.setCardBackgroundColor(itemView.context.resources.getColor(R.color.colorSecondary))
                } else {
                    surahCard.setCardBackgroundColor(itemView.context.resources.getColor(R.color.colorPrimary))
                }
                setOnClickListener { listener?.onClick(it, surah) }
            }
        }

    }
}
