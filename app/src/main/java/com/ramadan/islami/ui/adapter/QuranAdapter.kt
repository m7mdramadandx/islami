package com.ramadan.islami.ui.adapter

import android.text.Html
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Quran
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.ui.fragment.SurahDirections
import com.ramadan.islami.utils.*
import kotlinx.android.synthetic.main.item_ayah.view.*
import kotlinx.android.synthetic.main.item_surah.view.*
import java.lang.String.valueOf
import kotlin.math.max
import kotlin.math.min

class QuranAdapter : RecyclerView.Adapter<QuranAdapter.CustomView>() {
    private var surahList: MutableList<Surah> = mutableListOf()
    private var ayahList: MutableList<Quran.Ayah> = mutableListOf()
    private var surahName = String()
    private var surahNum = String()

    fun setSuraDataList(list: MutableList<Surah>) {
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
                surahName)
            else -> holder.surahView(surahList[position])
        }

    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val localeHelper = LocaleHelper()
        private val ctx = itemView.context

        @Suppress("DEPRECATION")
        fun surahView(surah: Surah) {
            localeHelper.getQuranMark(ctx)?.let {
                if (it.contains(surah.name.toRegex(RegexOption.LITERAL))) {
                    itemView.apply {
                        surahNumber.setTextColor(ctx.resources.getColor(R.color.grey_silver))
                        versesNumber.setTextColor(ctx.resources.getColor(R.color.grey_silver))
                        revelationType.setTextColor(ctx.resources.getColor(R.color.grey_silver))
                        surahCard.setCardBackgroundColor(ctx.resources.getColor(R.color.silver_grey))
                        surahImage.visibility = View.GONE
                    }
                } else {
                    itemView.apply {
                        versesNumber.setTextColor(ctx.resources.getColor(R.color.white))
                        versesNumber.setTextColor(ctx.resources.getColor(R.color.white))
                        revelationType.setTextColor(ctx.resources.getColor(R.color.white))
                        surahCard.setCardBackgroundColor(ctx.resources.getColor(R.color.colorSecondary))
                        surahImage.visibility = View.VISIBLE
                    }
                }
            }
            itemView.apply {
                surahNumber.text = valueOf(nf.format(surah.number))
                surahName.text = surah.name
                versesNumber.text =
                    context.getString(R.string.versesNumber) + valueOf(nf.format(surah.ayahs.size))
                juzNumber.text = surah.ayahs.first().juz
                revelationType.text = surah.revelationType
                setOnClickListener {
                    val action = SurahDirections.actionQuranToAyahFragment(surah)
                    it.changeNavigation(action)
                }
            }
        }

        fun ayahView(
            ayahList: MutableList<Quran.Ayah>,
            position: Int,
            surahName: String,
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
                            context.getString(R.string.hizbNumber) + valueOf(nf.format((it.hizbQuarter.toDouble() / 4)))
                    }
                }
                _surahName.text = surahName
                @Suppress("DEPRECATION")
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
                            .setIcon(R.drawable.ic_bookmark)
                        p1.add(1, tafsir, 1, context.getString(R.string.tafsir))
                            .setIcon(R.drawable.ic_story)
                        return true
                    }

                    override fun onDestroyActionMode(p0: ActionMode?) {
                    }

                    override fun onActionItemClicked(p0: ActionMode, p1: MenuItem): Boolean {
                        if (p1.itemId == bookmark) {
                            localeHelper.setQuranMark(
                                ctx,
                                "$surahName - صفحة رقم $position"
                            )
                            p0.finish()
                            return true
                        } else if (p1.itemId == tafsir) {
                            var ayahNum: Int = 0
                            var min = 0
                            var max: Int = ayahText.text.length
                            if (ayahText.isFocused) {
                                val selStart: Int = ayahText.selectionStart
                                val selEnd: Int = ayahText.selectionEnd
                                min = max(0, min(selStart, selEnd))
                                max = max(0, max(selStart, selEnd))
                            }
                            val selectedText: CharSequence = ayahText.text.subSequence(min, max)
                            ayahList.forEach {
                                if (it.text.contains(selectedText)) {
                                    ayahNum = it.numberInSurah
                                }
                            }
                            showBrief(
                                " تفسير الآية رقم ${valueOf(nf.format(ayahNum))} من $surahName ",
                                ayahList[ayahNum].tafseer,
                                ctx
                            )
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