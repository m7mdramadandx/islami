package com.ramadan.islami.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.ActionMode.Callback
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.type.DateTime
import com.ramadan.islami.R
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.api.RetrofitBuilder
import com.ramadan.islami.ui.viewModel.ViewModelFactory
import com.ramadan.islami.ui.viewModel.WebServiceViewModel
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.ResponseStatus
import com.ramadan.islami.utils.debug_tag
import kotlinx.android.synthetic.main.activity_hadith_of_day.*
import kotlin.math.max
import kotlin.math.min


class HadithOfDay : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this,
            ViewModelFactory(ApiHelper(RetrofitBuilder("https://api.sunnah.com/").apiService()))
        ).get(WebServiceViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hadith_of_day)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
//        val readonly = intent.getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false)
//        if (text != null) {
//            val intent = Intent()
//            intent.putExtra(Intent.EXTRA_PROCESS_TEXT, text.toString().toUpperCase())
//            setResult(Activity.RESULT_OK, intent)
////            intent.action = Intent.ACTION_VIEW
////            intent.addCategory(Intent.CATEGORY_BROWSABLE)
////            intent.data = Uri.parse("https://translate.google.com/$text")
////            startActivity(intent)
//        } else {
//            Toast.makeText(this, "Text cannot be modified", Toast.LENGTH_SHORT).show()
//        }
        val DEFINITION = 5
        hadithBody.customSelectionActionModeCallback = object : Callback {

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                // Remove the "select all" option
//                p1!!.removeItem(android.R.id.selectAll)
                // Remove the "cut" option
                p1!!.removeItem(android.R.id.cut)
                // Remove the "copy all" option
//                p1.removeItem(android.R.id.copy)
                return true
            }

            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                // Called when action mode is first created. The menu supplied
                // will be used to generate action buttons for the action mode

                // Here is an example MenuItem
                p1!!.add(0, DEFINITION, 0, "Translate").setIcon(R.drawable.menu)
                return true
            }

            override fun onDestroyActionMode(p0: ActionMode?) {
                // Called when an action mode is about to be exited and
                // destroyed
            }

            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
                when (p1!!.itemId) {
                    DEFINITION -> {
                        var min = 0
                        var max: Int = hadithBody.text.length
                        if (hadithBody.isFocused) {
                            val selStart: Int = hadithBody.selectionStart
                            val selEnd: Int = hadithBody.selectionEnd
                            min = max(0, min(selStart, selEnd))
                            max = max(0, max(selStart, selEnd))
                        }
                        // Perform your definition lookup with the selected text
                        val selectedText: CharSequence = hadithBody.text.subSequence(min, max)
                        val intent = Intent()
                        intent.action = Intent.ACTION_VIEW
                        intent.addCategory(Intent.CATEGORY_BROWSABLE)
                        intent.data = Uri.parse("https://translate.google.com/$selectedText")
                        startActivity(intent)
                        p0!!.finish()
                        return true
                    }
                    else -> {
                    }
                }
                return false
            }

        }
        setupObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupObservers() {
        viewModel.hadithOfDay().observe(this, {
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    progress.visibility = View.GONE
                    hadithBody.visibility = View.VISIBLE
                    hadithTitle.text = it.data!!.hadith[1].chapterTitle
                    val hadith =
                        it.data.hadith[1].body.removeSurrounding("<p>", "</p>").removePrefix("<br>")
                    hadithBody.text = hadith
                    LocaleHelper().setHadithOfDay(this,
                        hadith + DateTime.getDefaultInstance().day.toString())
                }
                ResponseStatus.ERROR -> {
                    progress.visibility = View.GONE
                    Log.e(debug_tag, it.message.toString())
                }
                ResponseStatus.LOADING -> {
                    Log.e(debug_tag, "LOADING")
                }
            }
        })
    }
}