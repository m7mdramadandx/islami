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

class Stories : Fragment(), Listener {
    private val viewModel by lazy { ViewModelProvider(this).get(DataViewModel::class.java) }
    private lateinit var storyAdapter: RecyclerViewAdapter
    private var isEnglish: Boolean = true
    private val localeHelper = LocaleHelper()

    override fun onAttach(context: Context) {
        isEnglish = localeHelper.getDefaultLanguage(context) == "en"
        super.onAttach(context)
        observeDate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.recycler_view, container, false)
        viewModel.listener = this
        storyAdapter = RecyclerViewAdapter()
        val recyclerView: RecyclerView = root.findViewById(R.id.global_recycler_view)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = storyAdapter
//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (dy > 0) supportActionBar?.hide() else supportActionBar?.show()
//            }
//        })
        return root
    }

    private fun observeDate() {
        viewModel.fetchStories(isEnglish).observe(this, { storyAdapter.setStoriesDataList(it) })
    }

    override fun onStarted() {
    }

    override fun onSuccess() {
        progress.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}