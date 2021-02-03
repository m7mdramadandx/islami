package com.ramadan.islami.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.GraphAdapter
import de.blox.graphview.Graph
import de.blox.graphview.GraphView
import de.blox.graphview.Node
import de.blox.graphview.layered.SugiyamaAlgorithm

class BigTree : AppCompatActivity() {
    var graphView: GraphView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prophets_tree)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        graphView = findViewById(R.id.graphView)
        val graph = Graph()
        val node1 = Node("Child 1")
        val node2 = Node("Child 2")
        val node3 = Node("Child 3")
        val node4 = Node("Child 4")
        val node5 = Node("Child 5")

        graph.addEdge(node1, node2)
        graph.addEdge(node1, node3)
        graph.addEdge(node4, node5)

        val graphAdapter = GraphAdapter(graph);

        graphView?.adapter = graphAdapter
        graphView?.setLayout(SugiyamaAlgorithm())
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}