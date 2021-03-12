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

    private var schedulePrayerData: PrayerData? = null
    private var prayerList = mutableListOf<PrayerData>()

    fun setSchedulePrayer(data: PrayerData) {
        schedulePrayerData = data
        notifyDataSetChanged()
    }

    fun setPrayerDataList(data: MutableList<PrayerData>) {
        prayerList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = if (schedulePrayerData != null)
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_prayer_time, parent, false)
        else LayoutInflater.from(parent.context)
            .inflate(R.layout.table_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when {
            schedulePrayerData != null -> holder.schedulePrayer(schedulePrayerData!!)
            prayerList.size > 0 -> holder.monthPrayers(prayerList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return when {
            schedulePrayerData != null -> 1
            prayerList.size > 0 -> prayerList.size
            else -> 0
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun schedulePrayer(prayerData: PrayerData) {
            itemView.apply {
                fajrPrayTime.text = prayerData.timings.fajr.removeSuffix("(EET)")
                sunrisePrayTime.text = prayerData.timings.sunrise.removeSuffix("(EET)")
                dhurPrayTime.text = prayerData.timings.dhuhr.removeSuffix("(EET)")
                asrPrayTime.text = prayerData.timings.asr.removeSuffix("(EET)")
                maghribPrayTime.text = prayerData.timings.maghrib.removeSuffix("(EET)")
                ishaPrayTime.text = prayerData.timings.isha.removeSuffix("(EET)")
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