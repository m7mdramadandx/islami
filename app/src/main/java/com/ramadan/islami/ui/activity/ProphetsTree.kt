package com.ramadan.islami.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.GraphAdapter
import com.ramadan.islami.utils.Utils
import de.blox.graphview.Graph
import de.blox.graphview.GraphView
import de.blox.graphview.tree.BuchheimWalkerAlgorithm
import de.blox.graphview.tree.BuchheimWalkerConfiguration


class ProphetsTree : AppCompatActivity() {
    var graphView: GraphView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prophets_tree)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        graphView = findViewById(R.id.graphView)
        val graph = Graph()
        val utils = Utils(this)
        graph.addEdge(utils.adam, utils.idriss)
        graph.addEdge(utils.idriss, utils.noah)
        graph.addEdge(utils.noah, utils.abraham)
        graph.addEdge(utils.noah, utils.methuselah)
        graph.addEdge(utils.noah, utils.heber)
        graph.addEdge(utils.abraham, utils.ishmael)
        graph.addEdge(utils.ishmael, utils.arabized_arabs)
        graph.addEdge(utils.arabized_arabs, utils.muhammad)
        graph.addEdge(utils.abraham, utils.isaac)
        graph.addEdge(utils.heber, utils.lot)
        graph.addEdge(utils.lot, utils.jethri)
        graph.addEdge(utils.isaac, utils.jacob)
        graph.addEdge(utils.isaac, utils.job)
        graph.addEdge(utils.job, utils.dhul_kifl)
        graph.addEdge(utils.jacob, utils.david)
        graph.addEdge(utils.david, utils.solomon)
        graph.addEdge(utils.solomon, utils.empty)
        graph.addEdge(utils.empty, utils.jesus)
        graph.addEdge(utils.solomon, utils.zacharia)
        graph.addEdge(utils.solomon, utils.john)
        graph.addEdge(utils.jacob, utils.joseph)
        graph.addEdge(utils.jacob, utils.jonah)
        graph.addEdge(utils.jacob, utils.moses)
        graph.addEdge(utils.jacob, utils.aron)
        graph.addEdge(utils.aron, utils.elias)
        graph.addEdge(utils.aron, utils.elisha)

        val graphAdapter = GraphAdapter(graph);

        graphView?.adapter = graphAdapter
        val configuration: BuchheimWalkerConfiguration = BuchheimWalkerConfiguration.Builder()
            .setSiblingSeparation(10)
            .setLevelSeparation(100)
            .setSubtreeSeparation(100)
            .build()
        graphView?.setLayout(BuchheimWalkerAlgorithm(configuration))
    }


}