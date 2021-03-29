package com.ramadan.islami.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Prayer.PrayerData
import com.ramadan.islami.utils.dateOfDay
import kotlinx.android.synthetic.main.item_month_prayer_times.view.*
import kotlinx.android.synthetic.main.item_prayer_time.view.*

class PrayTimeAdapter : RecyclerView.Adapter<PrayTimeAdapter.ViewHolder>() {

    private var schedulePrayerData: PrayerData? = null
    private var offlinePrayerData = mutableSetOf<String>()
    private var prayerList = mutableListOf<PrayerData>()

    fun setSchedulePrayer(data: PrayerData) {
        schedulePrayerData = data
        notifyDataSetChanged()
    }

    fun setOfflinePrayer(data: MutableSet<String>) {
        offlinePrayerData = data
        notifyDataSetChanged()
    }

    fun setPrayerDataList(data: MutableList<PrayerData>) {
        prayerList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = if (schedulePrayerData != null || offlinePrayerData.size > 0)
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_prayer_time, parent, false)
        else LayoutInflater.from(parent.context)
            .inflate(R.layout.item_month_prayer_times, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when {
            schedulePrayerData != null -> holder.schedulePrayer(schedulePrayerData!!)
            offlinePrayerData.size > 0 -> holder.offlinePrayer(offlinePrayerData)
            prayerList.size > 0 -> holder.monthPrayers(prayerList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return when {
            schedulePrayerData != null || offlinePrayerData.size > 0 -> 1
            prayerList.size > 0 -> prayerList.size
            else -> 0
        }
    }

    @Suppress("DEPRECATION")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun schedulePrayer(prayerData: PrayerData?) {
            itemView.apply {
                fajrPrayTime.text = prayerData?.timings?.fajr?.substring(0, 5) ?: "00:00"
                sunrisePrayTime.text = prayerData?.timings?.sunrise?.substring(0, 5) ?: "00:00"
                dhuhrPrayTime.text = prayerData?.timings?.dhuhr?.substring(0, 5) ?: "00:00"
                asrPrayTime.text = prayerData?.timings?.asr?.substring(0, 5) ?: "00:00"
                maghribPrayTime.text = prayerData?.timings?.maghrib?.substring(0, 5) ?: "00:00"
                ishaPrayTime.text = prayerData?.timings?.isha?.substring(0, 5) ?: "00:00"
            }
        }

        fun offlinePrayer(offlinePrayerData: MutableSet<String>) {
            itemView.apply {
                fajrPrayTime.text =
                    offlinePrayerData.find { it.contains("fajr") }?.substring(0, 5)
                sunrisePrayTime.text =
                    offlinePrayerData.find { it.contains("sunrise") }?.substring(0, 5)
                dhuhrPrayTime.text =
                    offlinePrayerData.find { it.contains("dhuhr") }?.substring(0, 5)
                asrPrayTime.text =
                    offlinePrayerData.find { it.contains("asr") }?.substring(0, 5)
                maghribPrayTime.text =
                    offlinePrayerData.find { it.contains("maghrib") }?.substring(0, 5)
                ishaPrayTime.text =
                    offlinePrayerData.find { it.contains("isha") }?.substring(0, 5)
            }
        }

        fun monthPrayers(prayerData: PrayerData, position: Int) {
            if (dateOfDay() == prayerData.date.gregorian.date) {
                itemView.apply {
                    table_row_layout.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    fajr.setTextColor(resources.getColor(R.color.white))
                    sunrise.setTextColor(resources.getColor(R.color.white))
                    dhuhr.setTextColor(resources.getColor(R.color.white))
                    asr.setTextColor(resources.getColor(R.color.white))
                    maghrib.setTextColor(resources.getColor(R.color.white))
                    isha.setTextColor(resources.getColor(R.color.white))
                    day.setTextColor(resources.getColor(R.color.colorAccent))
                }
            } else {
                itemView.apply {
                    table_row_layout.setBackgroundColor(resources.getColor(R.color.silver_grey))
                    fajr.setTextColor(resources.getColor(R.color.textColor))
                    sunrise.setTextColor(resources.getColor(R.color.textColor))
                    dhuhr.setTextColor(resources.getColor(R.color.textColor))
                    asr.setTextColor(resources.getColor(R.color.textColor))
                    maghrib.setTextColor(resources.getColor(R.color.textColor))
                    isha.setTextColor(resources.getColor(R.color.textColor))
                    day.setTextColor(resources.getColor(R.color.grey_silver))
                }
            }
            (prayerData.date.hijri.weekday.ar + "\n" + prayerData.date.gregorian.date).also {
                itemView.day.text = it
            }
            itemView.apply {
                fajr.text = prayerData.timings.fajr.substring(0, 5)
                sunrise.text = prayerData.timings.sunrise.substring(0, 5)
                dhuhr.text = prayerData.timings.dhuhr.substring(0, 5)
                asr.text = prayerData.timings.asr.substring(0, 5)
                maghrib.text = prayerData.timings.maghrib.substring(0, 5)
                isha.text = prayerData.timings.isha.substring(0, 5)
            }
        }
    }
}