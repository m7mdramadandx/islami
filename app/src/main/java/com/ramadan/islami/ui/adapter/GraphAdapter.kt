package com.ramadan.islami.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.snackbar.Snackbar
import com.ramadan.islami.R
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
        val dataNode = data.toString()
//            .removePrefix("Node(data=").removeSuffix(")")
        (viewHolder as SimpleViewHolder).textView.text = dataNode
        if (dataNode.contains("MUHAMMAD")) {
            viewHolder.nodeCard.setCardBackgroundColor(Color.rgb(0, 200, 83))
        }
        viewHolder.nodeCard.setOnClickListener {
            Snackbar.make(it, it.nodeText.text, Snackbar.LENGTH_LONG).show()
        }
    }

    class SimpleViewHolder(itemView: View) : GraphView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.nodeText)
        var nodeCard: CardView = itemView.findViewById(R.id.nodeCard)
    }
}