package com.ramadan.islami.ui.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.GraphAdapter
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import de.blox.graphview.Graph
import de.blox.graphview.GraphView
import de.blox.graphview.Node
import de.blox.graphview.tree.BuchheimWalkerAlgorithm
import de.blox.graphview.tree.BuchheimWalkerConfiguration


class MuhammadTree : AppCompatActivity() {
    var graphView: GraphView? = null
    lateinit var graph: Graph
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prophets_tree)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        graphView = findViewById(R.id.graphView)
        graph = Graph()
        graph.addEdge(Node(getString(R.string.hashim_ibn_abd_manaf)),
            Node(getString(R.string.abd_almuttalib)))
        graph.addEdge(Node(getString(R.string.abd_almuttalib)), Node(getString(R.string.abd_allah)))
        graph.addEdge(Node(getString(R.string.abd_allah)), Node(getString(R.string.muhammad)))
        graph.addEdge(Node(getString(R.string.muhammad)), Node(getString(R.string.khadija)))
        graph.addEdge(Node(getString(R.string.muhammad)), Node(getString(R.string.sawada)))
        graph.addEdge(Node(getString(R.string.muhammad)), Node(getString(R.string.aisha)))
        graph.addEdge(Node(getString(R.string.muhammad)), Node(getString(R.string.hafsa)))
        graph.addEdge(Node(getString(R.string.muhammad)), Node(getString(R.string.zainab)))
        graph.addEdge(Node(getString(R.string.muhammad)), Node(getString(R.string.umm_salama)))
        graph.addEdge(Node(getString(R.string.muhammad)), Node(getString(R.string.zainab1)))
        graph.addEdge(Node(getString(R.string.muhammad)), Node(getString(R.string.juwayriya)))
        graph.addEdge(Node(getString(R.string.muhammad)), Node(getString(R.string.umm_habiba)))
        graph.addEdge(Node(getString(R.string.muhammad)), Node(getString(R.string.safiyya)))
        graph.addEdge(Node(getString(R.string.muhammad)), Node(getString(R.string.maymuma)))
        graph.addEdge(Node(getString(R.string.muhammad)), Node(getString(R.string.maria)))
        graph.addEdge(Node(getString(R.string.khadija)), Node(getString(R.string.abd_allah1)))
        graph.addEdge(Node(getString(R.string.khadija)), Node(getString(R.string.al_qasim)))
        graph.addEdge(Node(getString(R.string.khadija)), Node(getString(R.string.zainab2)))
        graph.addEdge(Node(getString(R.string.khadija)), Node(getString(R.string.fatima)))
        graph.addEdge(Node(getString(R.string.fatima)), Node(getString(R.string.hussein)))
        graph.addEdge(Node(getString(R.string.fatima)), Node(getString(R.string.hassan)))
        graph.addEdge(Node(getString(R.string.fatima)), Node(getString(R.string.muhsin)))
        graph.addEdge(Node(getString(R.string.fatima)), Node(getString(R.string.umm_kalthum1)))
        graph.addEdge(Node(getString(R.string.fatima)), Node(getString(R.string.zainab3)))
        graph.addEdge(Node(getString(R.string.khadija)), Node(getString(R.string.umm_kalthum)))
        graph.addEdge(Node(getString(R.string.khadija)), Node(getString(R.string.ruqayyah)))
        graph.addEdge(Node(getString(R.string.ruqayyah)), Node(getString(R.string.abd_allah2)))

        val graphAdapter = GraphAdapter(graph);
        graphView?.adapter = graphAdapter
        val configuration: BuchheimWalkerConfiguration = BuchheimWalkerConfiguration.Builder()
            .setSiblingSeparation(100)
            .setLevelSeparation(300)
            .setSubtreeSeparation(500)
            .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_RIGHT_LEFT)
            .build()
        graphView?.setLayout(BuchheimWalkerAlgorithm(configuration))
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

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }

}