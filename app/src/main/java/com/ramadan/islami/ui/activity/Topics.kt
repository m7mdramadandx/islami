package com.ramadan.islami.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ramadan.islami.R
import com.ramadan.islami.ui.adapter.RecyclerViewAdapter
import com.ramadan.islami.ui.viewModel.DataViewModel
import com.ramadan.islami.ui.viewModel.Listener
import com.ramadan.islami.utils.LocaleHelper
import kotlinx.android.synthetic.main.recycler_view.*

class Topics : Fragment(), Listener {
    private val viewModel by lazy { ViewModelProvider(this).get(DataViewModel::class.java) }
    private var isEnglish: Boolean = true
    private val localeHelper = LocaleHelper()
    private lateinit var collectionAdapter: RecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isEnglish = localeHelper.getDefaultLanguage(context) == "en"
        observeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.recycler_view, container, false)
        recyclerView = root.findViewById(R.id.global_recycler_view)
        collectionAdapter = RecyclerViewAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = collectionAdapter
        viewModel.listener = this
        return root
    }

    private fun observeData() {
        viewModel.fetchCollection(isEnglish)
            .observe(this, { collectionAdapter.setCollectionsDataList(it) })
    }

    override fun onStarted() {}

    override fun onSuccess() {
        progress.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}