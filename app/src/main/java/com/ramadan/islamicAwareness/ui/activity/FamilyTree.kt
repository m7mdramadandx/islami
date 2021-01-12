package com.ramadan.islamicAwareness.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.ramadan.islamicAwareness.R


class FamilyTree : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.family_tree, container, false)
        view.findViewById<CardView>(R.id.a1).setOnClickListener {
            val intent = Intent(view.context, MuhammadTree::class.java)
            startActivity(intent)
        }
        view.findViewById<CardView>(R.id.a2).setOnClickListener {
            val intent = Intent(view.context, ProphetsTree::class.java)
            startActivity(intent)
        }
        view.findViewById<CardView>(R.id.a3).setOnClickListener {
            val intent = Intent(view.context, BigTree::class.java)
            startActivity(intent)
        }
        return view
    }
}