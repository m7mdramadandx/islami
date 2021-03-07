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
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.ui.activity.MainActivity
import com.ramadan.islami.ui.adapter.QuranAdapter
import com.ramadan.islami.ui.viewModel.QuranAyahViewModel

class AyahFragment : Fragment() {

    private lateinit var surah: Surah
    private lateinit var adapter: QuranAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel by lazy { ViewModelProvider(this).get(QuranAyahViewModel::class.java) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = QuranAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_quran_ayah, container, false)
        recyclerView = root.findViewById(R.id.rv_quran_ayah)
        arguments?.let { surah = AyahFragmentArgs.fromBundle(it).surah!! }
        (activity as MainActivity).supportActionBar?.title = surah.name
        adapter.setAyahDataList(surah.ayahs.toMutableList())
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        getDataAyah()
        return root
    }


    private fun getDataAyah() {
//        viewModel.getAyahBySura(requireContext(), sura.num.toString()).observe(viewLifecycleOwner, {
//            it.let { list -> adapter.updateList(list) }
//        })
    }
}
