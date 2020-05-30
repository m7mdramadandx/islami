package com.ramadan.theReminder

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import kotlinx.android.synthetic.main.story.*

class Story : AppCompatActivity() {
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.story)
        initMenuFragment()
        val bundle = intent.extras
        text.text = bundle?.getString("prophetName")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
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
            menuObjects = getMenuObjects(),
            isClosableOutside = true
        )

        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
            menuItemClickListener = { view, position ->
                Toast.makeText(
                    context,
                    "Clicked on position: $position",
                    Toast.LENGTH_SHORT
                ).show()
            }
            menuItemLongClickListener = { view, position ->
                Toast.makeText(
                    context,
                    "Long clicked on position: $position",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getMenuObjects() = mutableListOf<MenuObject>().apply {
        val empty = MenuObject().apply {}
        empty.setBgColorValue((Color.rgb(16, 24, 47)))
        val translate =
            MenuObject("Translate words").apply { setResourceValue(R.drawable.translate) }
        translate.setBgColorValue((Color.rgb(22, 36, 71)))
        val website = MenuObject("Open in website").apply { setResourceValue(R.drawable.website) }
        website.setBgColorValue((Color.rgb(23, 34, 59)))
        val favorite =
            MenuObject("Add to favorites").apply { setResourceValue(R.drawable.favorite) }
        favorite.setBgColorValue((Color.rgb(22, 36, 71)))
        add(empty)
        add(translate)
        add(website)
        add(favorite)
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }
}