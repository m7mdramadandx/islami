package com.ramadan.islami.ui.adapter

import android.text.Html
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.listener.SurahListener
import com.ramadan.islami.data.model.Quran
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.coloredJson
import com.ramadan.islami.utils.nf
import kotlinx.android.synthetic.main.item_ayah.view.*
import kotlinx.android.synthetic.main.item_surah.view.*
import java.lang.String.valueOf

class QuranAdapter : RecyclerView.Adapter<QuranAdapter.CustomView>() {
    private var listener: SurahListener? = null
    private var surahList: MutableList<Surah> = mutableListOf()
    private var ayahList: MutableList<Quran.Ayah> = mutableListOf()
    private var surahName = String()
    private var surahNum = String()

    fun setSuraDataList(list: MutableList<Surah>, listener: SurahListener) {
        this.listener = listener
        this.surahList = list
        notifyDataSetChanged()
    }

    fun setAyahDataList(surah: Surah) {
        this.ayahList = surah.ayahs.toMutableList()
        this.surahName = surah.name
        this.surahNum = surah.number.toString()
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
        return when {
            surahList.isNotEmpty() -> surahList.size
            ayahList.isNotEmpty() -> ayahList.last().page - ayahList.first().page + 1
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        return when {
            surahList.isNotEmpty() -> holder.surahView(surahList[position])
            ayahList.isNotEmpty() -> holder.ayahView(
                ayahList,
                ayahList.first().page + position,
                surahName,
                surahNum
            )
            else -> holder.surahView(surahList[position])
        }

    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val localeHelper = LocaleHelper()
        private val ctx = itemView.context

        fun surahView(surah: Surah) {
            if (localeHelper.getQuranMark(ctx).contains(surah.name.removePrefix("سورة "))) {
                itemView.apply {
                    versesNumber.setTextColor(ctx.resources.getColor(R.color.grey_silver))
                    revelationType.setTextColor(ctx.resources.getColor(R.color.grey_silver))
                    surahCard.setCardBackgroundColor(ctx.resources.getColor(R.color.silver_grey))
                }
            } else {
                itemView.apply {
                    versesNumber.setTextColor(ctx.resources.getColor(R.color.white))
                    revelationType.setTextColor(ctx.resources.getColor(R.color.white))
                    surahCard.setCardBackgroundColor(ctx.resources.getColor(R.color.colorPrimary))
                }
            }

            itemView.apply {
                surahNumber.text = surah.number.toString()
                surahName.text = surah.name
                versesNumber.text =
                    context.getString(R.string.versesNumber) + valueOf(nf.format(surah.ayahs?.size))
                juzNumber.text = surah.ayahs.first().juz
                revelationType.text = surah.revelationType
                setOnClickListener { listener?.onClick(it, surah) }
            }
        }

        fun ayahView(
            ayahList: MutableList<Quran.Ayah>,
            position: Int,
            surahName: String,
            surahNum: String
        ) {
            var text = String()
            itemView.apply {
                ayahList.forEach {
                    if (it.page == position) {
                        var ayahNumber =
                            " \uFD3F" + valueOf(nf.format(it.numberInSurah)) + "\uFD3E "
                        ayahNumber = ayahNumber.replace(ayahNumber, coloredJson(ayahNumber))
                        text += it.text + ayahNumber
                        _juzNumber.text = it.juz
                        _hizbNumber.text =
                            context.getString(R.string.hizbNumber) + valueOf(nf.format(it.hizbQuarter / 4))
                    }
                }
                _surahName.text = surahName
                ayahText.text = (Html.fromHtml(text))

                val bookmark = 0
                val tafsir = 1
                ayahText.customSelectionActionModeCallback = object : ActionMode.Callback {
                    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu): Boolean {
                        p1.removeItem(android.R.id.cut)
                        p1.removeItem(android.R.id.paste)
                        p1.removeItem(android.R.id.selectAll)
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            p1.removeItem(android.R.id.shareText)
                        }
                        return true
                    }

                    override fun onCreateActionMode(p0: ActionMode?, p1: Menu): Boolean {
                        p1.add(0, bookmark, 0, context.getString(R.string.bookmark))
                            .setIcon(R.drawable.menu)
                        p1.add(1, tafsir, 1, context.getString(R.string.tafsir))
                            .setIcon(R.drawable.menu)
                        return true
                    }

                    override fun onDestroyActionMode(p0: ActionMode?) {
                    }

                    override fun onActionItemClicked(p0: ActionMode, p1: MenuItem): Boolean {
                        if (p1.itemId == bookmark) {
                            localeHelper.setQuranMark(
                                ctx,
                                "${surahName.removePrefix("سورة ")} - $position"
                            )
                            p0.finish()
                            return true
                        } else if (p1.itemId == tafsir) {
                            localeHelper.setQuranMark(ctx, "$surahName - $position")
                            p0.finish()
                            return true
                        }
                        return false
                    }
                }
            }
        }
    }
}
