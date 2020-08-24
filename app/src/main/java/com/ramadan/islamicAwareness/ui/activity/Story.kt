@file:Suppress("DEPRECATION")

package com.ramadan.islamicAwareness.ui.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.islamicAwareness.R
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import kotlinx.android.synthetic.main.story_layout.*


class Story : AppCompatActivity() {
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment
    private var prophetName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.story_layout)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadData()
        initMenuFragment()

    }

    override fun onResume() {
        super.onResume()
        val bundle = intent.extras
        prophetName = bundle?.getString("prophetName").toString()
        section1.text = bundle?.getString("section1")
        section2.text = bundle?.getString("section2")
        section3.text = bundle?.getString("section3")
        section4.text = bundle?.getString("section4")
        section5.text = bundle?.getString("section5")
        section6.text = bundle?.getString("section6")
        section7.text = bundle?.getString("section7")
        supportActionBar?.title = prophetName

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when (it.itemId) {
                R.id.context_menu -> {
                    showContextMenuDialogFragment()
                }
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
                if (position == 1) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse("https://translate.google.com/")
                    startActivity(intent)
                } else if (position == 2) {
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
        val empty = MenuObject().apply {}
        empty.setBgColorValue((Color.rgb(23, 34, 59)))
        val translate =
            MenuObject("Translate words").apply { setResourceValue(R.drawable.translate) }
        translate.setBgColorValue((Color.rgb(22, 36, 71)))
        val wikipidia =
            MenuObject("Who's $prophetName").apply { setResourceValue(R.drawable.wikipedia) }
        wikipidia.setBgColorValue((Color.rgb(23, 34, 59)))
        add(empty)
        add(translate)
        add(wikipidia)
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }

    private fun loadData() {
        when {
            section2.text.length < 10 -> {
                layout2.visibility = INVISIBLE
                layout3.visibility = INVISIBLE
                layout4.visibility = INVISIBLE
                layout5.visibility = INVISIBLE
                layout6.visibility = INVISIBLE
                layout7.visibility = INVISIBLE
            }
            section3.text.length < 10 -> {
                layout3.visibility = INVISIBLE
                layout4.visibility = INVISIBLE
                layout5.visibility = INVISIBLE
                layout6.visibility = INVISIBLE
                layout7.visibility = INVISIBLE
            }
            section4.text.length < 10 -> {
                layout4.visibility = INVISIBLE
                layout5.visibility = INVISIBLE
                layout6.visibility = INVISIBLE
                layout7.visibility = INVISIBLE
            }
            section5.text.length < 10 -> {
                layout5.visibility = INVISIBLE
                layout6.visibility = INVISIBLE
                layout7.visibility = INVISIBLE
            }
            section6.text.length < 10 -> {
                layout6.visibility = INVISIBLE
                layout7.visibility = INVISIBLE
            }
            section7.text.length < 10 -> {
                layout7.visibility = INVISIBLE
            }
        }
    }

}