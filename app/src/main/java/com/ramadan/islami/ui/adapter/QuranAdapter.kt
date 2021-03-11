package com.ramadan.islami.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.listener.SurahListener
import com.ramadan.islami.data.model.Quran
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.utils.nf
import kotlinx.android.synthetic.main.item_ayah.view.*
import kotlinx.android.synthetic.main.item_surah.view.*
import java.lang.String.valueOf

class QuranAdapter : RecyclerView.Adapter<QuranAdapter.CustomView>() {
    var listener: SurahListener? = null
    private var surahList: MutableList<Surah> = mutableListOf()
    private var ayahList: MutableList<Quran.Ayah> = mutableListOf()
    var surahName = String()

    fun setSuraDataList(list: MutableList<Surah>, listener: SurahListener) {
        this.listener = listener
        this.surahList = list
        notifyDataSetChanged()
    }

    fun setAyahDataList(surah: Surah) {
        this.ayahList = surah.ayahs.toMutableList()
        this.surahName = surah.name
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): QuranAdapter.CustomView {
        val view: View = when {
            surahList.size > 0 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_surah, parent, false)
            ayahList.size > 0 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ayah, parent, false)
            else -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ayah, parent, false)
        }
        return CustomView(view)
    }

    override fun getItemCount(): Int {
        return if (surahList.size > 0) surahList.size else ayahList.last().page - ayahList.first().page + 1
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        return if (surahList.size > 0) holder.surahView(surahList[position])
        else holder.ayahView(ayahList, ayahList.first().page + position, surahName)

    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun surahView(surah: Surah) {
            itemView.apply {
                surahNumber.text = surah.number.toString()
                surahName.text = surah.name
                versesNumber.text =
                    context.getString(R.string.versesNumber) + valueOf(nf.format(surah.ayahs.size))
                juzNumber.text = surah.ayahs.first().juz
                revelationType.text = surah.revelationType
                setOnClickListener { listener?.onClick(it, surah) }
            }
        }

        fun ayahView(ayahList: MutableList<Quran.Ayah>, position: Int, surahName: String) {
            var text = String()
            itemView.apply {
                ayahList.forEach {
                    if (it.page == position) {
                        var ayahNumber =
                            " \uFD3F" + valueOf(nf.format(it.numberInSurah)) + "\uFD3E "
                        ayahNumber = ayahNumber.replace(ayahNumber,
                            "<font color='#E1B34F'>$ayahNumber</font>")
                        text += it.text + ayahNumber
                        _juzNumber.text = it.juz
                        _hizbNumber.text =
                            context.getString(R.string.hizbNumber) + valueOf(nf.format(it.hizbQuarter / 4))
                    }
                }
                _surahName.text = surahName
                ayahText.text = (Html.fromHtml(text))

            }
        }
    }
}
