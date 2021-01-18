package com.ramadan.islamicAwareness.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.islamicAwareness.R
import kotlinx.android.synthetic.main.family_tree.*


class FamilyTree : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.family_tree)
        a1.setOnClickListener { startActivity(Intent(this, ProphetsTree::class.java)) }
        a2.setOnClickListener { startActivity(Intent(this, ProphetsTree::class.java)) }
        a3.setOnClickListener { startActivity(Intent(this, BigTree::class.java)) }
    }
}