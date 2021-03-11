package com.ramadan.islami.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.PrayerData
import com.ramadan.islami.utils.dateOfDay
import kotlinx.android.synthetic.main.item_prayer_time.view.*
import kotlinx.android.synthetic.main.table_row.view.*

class TableAdapter : RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    private var schedulePrayerData = mutableListOf<PrayerData>()
    private var prayerList = mutableListOf<PrayerData>()

    fun setSchedulePrayer(data: MutableList<PrayerData>) {
        schedulePrayerData = data
        notifyDataSetChanged()
    }

    fun setPrayerDataList(data: MutableList<PrayerData>) {
        prayerList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = if (schedulePrayerData.size > 0)
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_prayer_time, parent, false)
        else LayoutInflater.from(parent.context)
            .inflate(R.layout.table_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when {
            schedulePrayerData.isNotEmpty() -> holder.schedulePrayer(prayerList[position], position)
            prayerList.isNotEmpty() -> holder.monthPrayers(prayerList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return when {
            schedulePrayerData.isNotEmpty() -> schedulePrayerData.size
            prayerList.size > 0 -> prayerList.size
            else -> 0
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun schedulePrayer(prayerData: PrayerData, position: Int) {
            itemView.apply {
                tv_pray_time_fajr.text = prayerData.timings.fajr.removeSuffix("(EET)")
                tv_pray_time_shurooq.text = prayerData.timings.sunrise.removeSuffix("(EET)")
                tv_pray_time_dhuhr.text = prayerData.timings.dhuhr.removeSuffix("(EET)")
            }
        }

        fun monthPrayers(prayerData: PrayerData, position: Int) {
            if (dateOfDay() == prayerData.date.gregorian.date) {
                itemView.table_row_layout.setBackgroundColor(Color.LTGRAY)
            } else {
                itemView.table_row_layout.setBackgroundColor(itemView.resources.getColor(R.color.silver_grey))
            }
            (prayerData.date.hijri.weekday.ar + "\n" + prayerData.date.gregorian.date).also {
                itemView.day.text = it
            }
            itemView.fajr.text = prayerData.timings.fajr.removeSuffix("(EET)")
            itemView.sunrise.text = prayerData.timings.sunrise.removeSuffix("(EET)")
            itemView.dhuhr.text = prayerData.timings.dhuhr.removeSuffix("(EET)")
            itemView.asr.text = prayerData.timings.asr.removeSuffix("(EET)")
            itemView.maghrib.text = prayerData.timings.maghrib.removeSuffix("(EET)")
            itemView.isha.text = prayerData.timings.isha.removeSuffix("(EET)")

        }

    }

}