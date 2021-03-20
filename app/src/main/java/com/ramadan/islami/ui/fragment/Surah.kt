package com.ramadan.islami.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.QuranAdapter
import com.ramadan.islami.ui.viewModel.LocalViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Surah : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(LocalViewModel::class.java) }
    private lateinit var recyclerView: RecyclerView
    private lateinit var quranAdapter: QuranAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        quranAdapter = QuranAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_sura, container, false)
        recyclerView = root.findViewById(R.id.rv_quran_sura)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = quranAdapter
        getDataSura()
    }

    private fun getDataSura() {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Main) {
                val list = viewModel.getAllSura(requireContext())
                quranAdapter.setSuraDataList(list)
            }
        }
    }

}
