package com.ramadan.islami.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Calender
import com.ramadan.islami.data.model.PrayerData
import com.ramadan.islami.utils.dateOfDay
import kotlinx.android.synthetic.main.table_row.view.*

class TableAdapter : RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    private var prayerList = mutableListOf<PrayerData>()
    private var calenderList = mutableListOf<Calender>()

    fun setPrayerDataList(data: MutableList<PrayerData>) {
        prayerList = data
        notifyDataSetChanged()
    }

    fun setCalenderDataList(data: MutableList<Calender>) {
        calenderList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.table_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when {
            prayerList.isNotEmpty() -> holder.prayerListView(prayerList[position], position)
            calenderList.isNotEmpty() -> holder.calenderView(calenderList[position])
        }
    }

    override fun getItemCount(): Int {
        return when {
            prayerList.isNotEmpty() -> prayerList.size
            calenderList.size > 0 -> calenderList.size
            else -> 0
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun prayerListView(prayerData: PrayerData, position: Int) {
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

        fun calenderView(calender: Calender) {
        }

    }
}