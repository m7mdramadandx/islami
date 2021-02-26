package com.ramadan.islami.ui.activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.data.model.PrayerData
import com.ramadan.islami.ui.viewModel.ApiViewModel
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.utils.ResStatus
import com.ramadan.islami.utils.dateOfDay
import com.ramadan.islami.utils.debug_tag
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import kotlinx.android.synthetic.main.date_conversion.*
import java.util.*


class DateConversion : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this,
            ViewModelFactory(ApiHelper(RetrofitBuilder("http://api.aladhan.com/").hijriCalender()))
        ).get(ApiViewModel::class.java)
    }
    private lateinit var result: PrayerData
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment
    private lateinit var _calender: Calendar

    override fun onStart() {
        super.onStart()
        observeDate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_conversion)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initMenuFragment()
    }

    private fun observeDate() {
        viewModel.fetchPrayers().observe(this, {
            when (it.status) {
                ResStatus.LOADING -> progress.visibility = View.VISIBLE
                ResStatus.SUCCESS -> {
                    it.data!!.data.forEach { prayerData ->
                        if (dateOfDay() == prayerData.date.gregorian.date) result = prayerData
                    }
                    progress.visibility = View.GONE
//                    title = it.data!!.data.first().date.hijri.month.ar
                    _calender = Calendar.getInstance()
//                    showDate(year, month+1, day);
                    showDate(
                        result.date.hijri.year.toInt(),
                        result.date.hijri.month.number,
                        result.date.hijri.day.toInt(),
                    )
                    calender.date = _calender.timeInMillis
//                    calender.date = result.date.hijri.date.toLong()
//                    Log.e(debug_tag, result.date.hijri.date.toLong().toString())
//                    Log.e(debug_tag, result.date.hijri.year.toLong().toString())
                }
                ResStatus.ERROR -> {
                    progress.visibility = View.GONE
                    Log.e(debug_tag, it.message.toString())
                }
            }
        })
    }

    private val myDateListener: OnDateSetListener =
        OnDateSetListener { arg0, arg1, arg2, arg3 -> // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3)
        }

    private fun showDate(year: Int, month: Int, day: Int) {
        dateView.text = StringBuilder().append(day).append("/")
            .append(month).append("/").append(year)
    }

    override fun onCreateDialog(id: Int): Dialog? {
        return if (id == 999) {
            DatePickerDialog(
                this,
                myDateListener,
                result.date.hijri.year.toInt(),
                result.date.hijri.month.number,
                result.date.hijri.day.toInt(),
            )
        } else null
    }

    fun setDate(view: View?) {
        showDialog(999)
        Toast.makeText(applicationContext, "ca",
            Toast.LENGTH_SHORT)
            .show()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.let { if (it.itemId == R.id.context_menu) showContextMenuDialogFragment() }
        return super.onOptionsItemSelected(item)
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }

    private fun initMenuFragment() {
        val menuParams = MenuParams(
            actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
            menuObjects = getMenuObjects(),
            isClosableOutside = true
        )
        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
            menuItemClickListener = { view, position ->
                if (position == 0) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse("https://translate.google.com/")
                    startActivity(intent)
                }
            }
        }
    }

    private fun getMenuObjects() = mutableListOf<MenuObject>().apply {
        MenuObject(getString(R.string.view_images)).apply {
            setResourceValue(R.drawable.photo_library)
            setBgColorValue((Color.rgb(22, 36, 71)))
            add(this)
        }
    }


}