package com.ramadan.islami.ui.activity

//import com.ramadan.islami.data.model.Qibla
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.ui.viewModel.MainViewModel
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.utils.ResStatus
import com.ramadan.islami.utils.debug_tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {


    private val viewModel by lazy {
        ViewModelProvider(this,
            ViewModelFactory(ApiHelper(RetrofitBuilder("http://api.aladhan.com/").hijriCalender()))
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hadith_of_day)
        GlobalScope.launch((Dispatchers.Main)) {
            withContext(Dispatchers.IO) { }
        }
        setupObservers()
        setupObservers2()
    }

    private fun setupObservers() {
        viewModel.hijriCalender().observe(this, {
            when (it.status) {
                ResStatus.SUCCESS -> {
                    Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show()
                    val x = it.data
                    Log.e(debug_tag, x!!.data.hijri.toString())
//                        resource.data?.let(this::retrieveList)
                }
                ResStatus.ERROR -> Log.e(debug_tag, it.message.toString())
                ResStatus.LOADING -> Log.e(debug_tag, "LOADING")
            }
        })
    }

    private fun setupObservers2() {
        viewModel.fetchPrayers().observe(this, {
            when (it.status) {
                ResStatus.SUCCESS -> {
                    Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show()
                    val x = it.data
//                    Log.e(debug_tag, x!!.data.last().date.toString())
//                        resource.data?.let(this::retrieveList)
                }
                ResStatus.ERROR -> Log.e(debug_tag, it.message.toString())
                ResStatus.LOADING -> Log.e(debug_tag, "LOADING")
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