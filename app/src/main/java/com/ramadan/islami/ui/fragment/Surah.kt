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
import com.ramadan.islami.data.listener.SurahListener
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.ui.adapter.QuranAdapter
import com.ramadan.islami.ui.viewModel.QuranViewModel
import com.ramadan.islami.utils.changeNavigation
import kotlinx.coroutines.*


class Surah : Fragment(), SurahListener {

    private val viewModel by lazy { ViewModelProvider(this).get(QuranViewModel::class.java) }
    private lateinit var recyclerView: RecyclerView
    private lateinit var quranAdapter: QuranAdapter
    private lateinit var searchView: PersistentSearchView


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
//        searchView = root.findViewById(R.id.persistent_search_view)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = quranAdapter
        getDataSura()
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
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Main) {
                val list = viewModel.getAllSura(requireContext(), search)
                quranAdapter.setSuraDataList(list, this@Surah)
            }
        }
    }


    override fun onClick(view: View, surah: Surah) {
        val action = SurahDirections.actionNavQuranToAyahFragment(surah)
        view.changeNavigation(action)
    }
}
