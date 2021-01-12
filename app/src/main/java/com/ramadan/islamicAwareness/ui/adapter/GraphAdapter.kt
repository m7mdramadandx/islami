package com.ramadan.islamicAwareness.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.ramadan.islamicAwareness.R
import de.blox.graphview.Graph
import de.blox.graphview.GraphAdapter
import de.blox.graphview.GraphView
import kotlinx.android.synthetic.main.node.view.*


class GraphAdapter(itemView: Graph) : GraphAdapter<GraphView.ViewHolder>(itemView) {
    override fun getCount(): Int = graph.nodeCount

    override fun getItem(position: Int): Any = graph.getNodeAtPosition(position)

    override fun isEmpty(): Boolean = graph.hasNodes()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraphView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.node, parent, false)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: GraphView.ViewHolder, data: Any, position: Int) {
        (viewHolder as SimpleViewHolder).textView.text =
            data.toString().removePrefix("Node(data=").removeSuffix(")")
        viewHolder.textView.setOnClickListener {
            Snackbar.make(it, it.nodeText.text, Snackbar.LENGTH_LONG).setBackgroundTint(Color.WHITE)
                .show()
        }
    }

    class SimpleViewHolder(itemView: View) : GraphView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.nodeText)
    }
}