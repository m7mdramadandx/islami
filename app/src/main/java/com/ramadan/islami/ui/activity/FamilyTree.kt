package com.ramadan.islami.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.islami.R
import kotlinx.android.synthetic.main.family_tree.*


class FamilyTree : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.family_tree)
        muhammadTree.setOnClickListener { startActivity(Intent(this, MuhammadTree::class.java)) }
        prophets_family_tree.setOnClickListener {
            startActivity(Intent(this,
                ProphetsTree::class.java))
        }
        the_big_family_tree.setOnClickListener { startActivity(Intent(this, BigTree::class.java)) }
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}