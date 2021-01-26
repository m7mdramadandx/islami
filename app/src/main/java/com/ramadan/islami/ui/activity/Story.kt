@file:Suppress("DEPRECATION")

package com.ramadan.islami.ui.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.islami.R
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import kotlinx.android.synthetic.main.story_layout.*


class Story : AppCompatActivity() {
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment
    private var prophetName: String? = "null"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.story_layout)
        val bundle = intent.extras
        prophetName = bundle?.getString("prophetName")
        val text = bundle?.getStringArrayList("text")
        text?.elementAtOrNull(0).also { section1.text = it }
        text?.elementAtOrNull(1).also { section2.text = it }
        text?.elementAtOrNull(2).also { section3.text = it }
        text?.elementAtOrNull(3).also { section4.text = it }
        text?.elementAtOrNull(4).also { section5.text = it }
        text?.elementAtOrNull(5).also { section6.text = it }
        text?.elementAtOrNull(6).also { section7.text = it }

        supportActionBar?.title = prophetName
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        layoutVisibility()
        initMenuFragment()

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
        item.let {
            when (it.itemId) {
                R.id.context_menu -> showContextMenuDialogFragment()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initMenuFragment() {
        val menuParams = MenuParams(
            actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
            animationDuration = 250,
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
                } else if (position == 1) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse("https://en.wikipedia.org/wiki/$prophetName")
                    startActivity(intent)
                }
            }
        }
    }

    private fun getMenuObjects() = mutableListOf<MenuObject>().apply {
        MenuObject("Translate words").apply {
            setResourceValue(R.drawable.translate)
            setBgColorValue((Color.rgb(22, 36, 71)))
            add(this)
        }
        MenuObject("Who's $prophetName").apply {
            setResourceValue(R.drawable.wikipedia)
            setBgColorValue((Color.rgb(23, 34, 59)))
            add(this)
        }
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }

    private fun layoutVisibility() {
        when {
            section2.text.length < 10 -> {
                layout2.visibility = GONE
                layout3.visibility = GONE
                layout4.visibility = GONE
                layout5.visibility = GONE
                layout6.visibility = GONE
                layout7.visibility = GONE
            }
            section3.text.length < 10 -> {
                layout3.visibility = GONE
                layout4.visibility = GONE
                layout5.visibility = GONE
                layout6.visibility = GONE
                layout7.visibility = GONE
            }
            section4.text.length < 10 -> {
                layout4.visibility = GONE
                layout5.visibility = GONE
                layout6.visibility = GONE
                layout7.visibility = GONE
            }
            section5.text.length < 10 -> {
                layout5.visibility = GONE
                layout6.visibility = GONE
                layout7.visibility = GONE
            }
            section6.text.length < 10 -> {
                layout6.visibility = GONE
                layout7.visibility = GONE
            }
            section7.text.length < 10 -> {
                layout7.visibility = GONE
            }
        }
    }

}