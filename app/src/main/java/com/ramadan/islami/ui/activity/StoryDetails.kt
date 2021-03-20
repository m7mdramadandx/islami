package com.ramadan.islami.ui.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Story
import com.ramadan.islami.ui.activity.MainActivity.Companion.language
import com.ramadan.islami.ui.adapter.StoryAdapter
import com.ramadan.islami.ui.viewModel.FirebaseViewModel
import com.ramadan.islami.utils.Utils
import com.ramadan.islami.utils.showBrief
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuGravity
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoryDetails : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(FirebaseViewModel::class.java) }
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment
    private lateinit var story: Story
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.global_recycler_view)
        progress.visibility = View.GONE
        storyAdapter = StoryAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = storyAdapter
        initMenuFragment()
        if (intent.hasExtra("story")) {
            story = intent.getSerializableExtra("story") as Story
            observeData()
        } else fetchNotification()
    }

    private fun observeData() {
        storyAdapter.setStoriesDataList(story.title, story.text)
        supportActionBar?.title = story.title
    }

    private fun fetchNotification() {
        val storyID = intent.getStringExtra("storyID").toString()
        GlobalScope.launch(Dispatchers.IO) {
            story = viewModel.fetchStory(language, storyID)
            withContext(Dispatchers.Main) { observeData() }
        }
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

    private fun initMenuFragment() {
        val menuParams = MenuParams(
            actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
            menuObjects = getMenuObjects(),
            isClosableOutside = true,
            gravity = MenuGravity.END
        )
        val utils = Utils(applicationContext)
        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
            menuItemClickListener = { view, position ->
                when (position) {
                    0 -> showBrief(story.title, story.brief, view.context)
                    1 -> {
                        val intent = Intent()
                        intent.action = Intent.ACTION_VIEW
                        intent.addCategory(Intent.CATEGORY_BROWSABLE)
                        intent.data = Uri.parse("https://translate.google.com/")
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent()
                        intent.action = Intent.ACTION_VIEW
                        intent.addCategory(Intent.CATEGORY_BROWSABLE)
                        intent.data = Uri.parse("https://en.wikipedia.org/wiki/$title")
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun getMenuObjects() = mutableListOf<MenuObject>().apply {
        MenuObject(getString(R.string.brief)).apply {
            setResourceValue(R.drawable.ic_quote)
            setBgColorValue((Color.rgb(23, 34, 59)))
            add(this)
        }
        MenuObject("Translate words").apply {
            setResourceValue(R.drawable.translate)
            setBgColorValue((Color.rgb(22, 36, 71)))
            add(this)
        }
        MenuObject("Who's $title").apply {
            setResourceValue(R.drawable.wikipedia)
            setBgColorValue((Color.rgb(23, 34, 59)))
            add(this)
        }
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager,
                ContextMenuDialogFragment.TAG)
        }
    }

}