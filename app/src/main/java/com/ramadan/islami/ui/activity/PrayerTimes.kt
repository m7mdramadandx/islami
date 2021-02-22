package com.ramadan.islami.ui.activity

//import com.ramadan.islami.data.model.Qibla
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.data.model.PrayerData
import com.ramadan.islami.ui.adapter.TableAdapter
import com.ramadan.islami.ui.viewModel.MainViewModel
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.utils.ResStatus
import com.ramadan.islami.utils.debug_tag
import kotlinx.android.synthetic.main.table_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrayerTimes : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this,
            ViewModelFactory(ApiHelper(RetrofitBuilder("http://api.aladhan.com/").hijriCalender()))
        ).get(MainViewModel::class.java)
    }
    private lateinit var tableAdapter: TableAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_layout)
        recyclerView = findViewById(R.id.table_recycler_view)
        tableAdapter = TableAdapter()
        recyclerView.adapter = tableAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch((Dispatchers.Main)) {
            withContext(Dispatchers.IO) { }
        }
        setupObservers()
        setupObservers2()
    }

    private fun setupObservers() {
        viewModel.hijriCalender().observe(this, {
            when (it.status) {
                ResStatus.LOADING -> progress.visibility = View.VISIBLE
                ResStatus.SUCCESS -> {
                    progress.visibility = View.GONE
                    Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show()
//                    tableAdapter.setPrayerDataList(it.data?.data as MutableList<PrayerData>)
                }
                ResStatus.ERROR -> {
                    progress.visibility = View.GONE
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    Log.e(debug_tag, it.message.toString())
                }
            }
        })
    }

    private fun setupObservers2() {
        viewModel.fetchPrayers().observe(this, {
            when (it.status) {
                ResStatus.LOADING -> progress.visibility = View.VISIBLE
                ResStatus.SUCCESS -> {
                    progress.visibility = View.GONE
                    month.text = it.data!!.data.first().date.hijri.month.ar
                    tableAdapter.setPrayerDataList(it.data?.data as MutableList<PrayerData>)
                }
                ResStatus.ERROR -> {
                    progress.visibility = View.GONE
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    Log.e(debug_tag, it.message.toString())
                }
            }
        })
    }

//    private fun retrieveList(users: Prayers) {
//        println(users.data)
//        println(users[1])
//        adapter.apply {
//            addUsers(users)
//            notifyDataSetChanged()
//        }
//    }
}