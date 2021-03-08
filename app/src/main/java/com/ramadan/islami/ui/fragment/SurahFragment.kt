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
import com.paulrybitskyi.persistentsearchview.PersistentSearchView
import com.ramadan.islami.R
import com.ramadan.islami.data.listener.SurahRecyclerListener
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.ui.adapter.QuranAdapter
import com.ramadan.islami.ui.viewModel.QuranSuraViewModel
import com.ramadan.islami.utils.changeNavigation


class SurahFragment : Fragment(), SurahRecyclerListener {

    private val viewModel by lazy { ViewModelProvider(this).get(QuranSuraViewModel::class.java) }
    private lateinit var recyclerView: RecyclerView
    private lateinit var view: QuranAdapter
    private lateinit var searchView: PersistentSearchView


    override fun onAttach(context: Context) {
        super.onAttach(context)
        view = QuranAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_quran_sura, container, false)
        recyclerView = root.findViewById(R.id.rv_quran_sura)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = view
//        searchView = root.findViewById(R.id.persistent_search_view)
        getDataSura()
        return root
    }

    private fun getDataSura() {
        getSuraSearch("")
//        with(searchView) {
//            setOnSearchConfirmedListener { searchView, query ->
//                searchView.collapse()
//                getSuraSearch(query)
//            }
//            hideLeftButton()
//            hideRightButton()
//            isVoiceInputButtonEnabled = false
//            setOnClearInputBtnClickListener { getSuraSearch("") }
    }


    private fun getSuraSearch(search: String) {
        val list = viewModel.getAllSura(requireContext(), search)
        view.setSuraDataList(list, this)
    }


    override fun onClick(view: View, _surah: Surah) {
        val action = SurahFragmentDirections.actionNavQuranToAyahFragment(_surah)
        view.changeNavigation(action)
    }
}
