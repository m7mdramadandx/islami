package com.ramadan.islami.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.RecyclerViewAdapter
import com.ramadan.islami.utils.dailyMutableList

class Daily : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var recyclerViewAdapter = RecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
//        isEnglish = localeHelper.getDefaultLanguage(context) == "en"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.recycler_view, container, false)
        recyclerView = root.findViewById(R.id.global_recycler_view)
        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.adapter = recyclerViewAdapter
        return root
    }

    private fun observeData() {
        recyclerViewAdapter.setDailyDataList(dailyMutableList)
    }

}