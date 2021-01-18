package com.ramadan.islamicAwareness.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.islamicAwareness.R
import com.ramadan.islamicAwareness.ui.adapter.GraphAdapter
import com.ramadan.islamicAwareness.utils.Utils
import de.blox.graphview.Graph
import de.blox.graphview.GraphView
import de.blox.graphview.Node
import de.blox.graphview.tree.BuchheimWalkerAlgorithm
import de.blox.graphview.tree.BuchheimWalkerConfiguration

class MuhammadTree : AppCompatActivity() {
    var graphView: GraphView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prophets_tree)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        graphView = findViewById(R.id.graphView)
        val graph = Graph()
        val utils = Utils(this)

        graph.addEdge(utils.hashim_ibn_abd_manaf, utils.abd_almuttalib)
        graph.addEdge(utils.abd_almuttalib, utils.abd_allah)
//        graph.addEdge(utils.aminah_bint_wahab, utils.abd_allah)
        graph.addEdge(utils.abd_allah, utils.muhammad)
        graph.addEdge(utils.muhammad, utils.khadija)
        graph.addEdge(utils.muhammad, utils.sawada)
        graph.addEdge(utils.muhammad, utils.aisha)
        graph.addEdge(utils.muhammad, utils.hafsa)
        graph.addEdge(utils.muhammad, utils.zainab1)
        graph.addEdge(utils.muhammad, utils.umm_salama)
        graph.addEdge(utils.muhammad, utils.zainab2)
        graph.addEdge(utils.muhammad, utils.juwayriya)
        graph.addEdge(utils.muhammad, utils.umm_habiba)
        graph.addEdge(utils.muhammad, utils.safiyya)
        graph.addEdge(utils.muhammad, utils.maymuma)
        graph.addEdge(utils.muhammad, utils.maria)
//        graph.addEdge(utils.khadija, Node(getString(R.string.abd_allah)))
        graph.addEdge(utils.khadija, utils.al_qasim)
        graph.addEdge(utils.khadija, utils.zainab)
        graph.addEdge(utils.khadija, utils.fatima)
        graph.addEdge(utils.fatima, utils.hussein)
        graph.addEdge(utils.fatima, utils.hassan)
        graph.addEdge(utils.fatima, utils.muhsin)
        graph.addEdge(utils.fatima, utils.umm_kalthum)
//        graph.addEdge(utils.fatima, utils.zainab)
        val umm_kalthum = Node(getString(R.string.umm_kalthum))
//        graph.addEdge(utils.khadija, umm_kalthum)
        graph.addEdge(utils.khadija, utils.ruqayyah)
//        graph.addEdge(utils.ruqayyah, Node(getString(R.string.abd_allah)))

        val graphAdapter = GraphAdapter(graph);

        graphView?.adapter = graphAdapter
        val configuration: BuchheimWalkerConfiguration = BuchheimWalkerConfiguration.Builder()
            .setSiblingSeparation(100)
            .setLevelSeparation(100)
            .setSubtreeSeparation(50)
            .build()
        graphView?.setLayout(BuchheimWalkerAlgorithm(configuration))
    }
}