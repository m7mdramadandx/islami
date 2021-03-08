package com.ramadan.islami.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.ui.activity.MainActivity
import com.ramadan.islami.ui.adapter.DoppelgangerAdapter
import com.ramadan.islami.ui.adapter.QuranAdapter
import com.ramadan.islami.ui.viewModel.QuranAyahViewModel
import kotlinx.android.synthetic.main.activity_ayah.*

class AyahFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(QuranAyahViewModel::class.java) }
    private lateinit var surah: Surah
    private lateinit var adapter: QuranAdapter
    private lateinit var recyclerView: RecyclerView
    private var toast: Toast? = null
    private lateinit var doppelgangerViewPager: ViewPager2
    private lateinit var doppelgangerAdapter: DoppelgangerAdapter

    private var doppelgangerPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            Toast.makeText(requireContext(),
                "Selected position: $position",
                Toast.LENGTH_SHORT).show()
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let { surah = AyahFragmentArgs.fromBundle(it).surah }
        (activity as MainActivity).supportActionBar?.title = surah.name
        doppelgangerAdapter = DoppelgangerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.activity_ayah, container, false)
        doppelgangerViewPager = root.findViewById(R.id.doppelgangerViewPager)
//        surahName.text = surah.name
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doppelgangerAdapter.setAyahDataList(surah.ayahs.toMutableList())
        doppelgangerViewPager.adapter = doppelgangerAdapter
        doppelgangerViewPager.registerOnPageChangeCallback(doppelgangerPageChangeCallback)
        TabLayoutMediator(tabLayout, doppelgangerViewPager) { tab, position ->
            tab.text = "${position + 1}"
        }.attach()
        doppelgangerViewPager.layoutDirection = ViewPager2.LAYOUT_DIRECTION_RTL
        tabLayout.layoutDirection = View.LAYOUT_DIRECTION_RTL

    }


    private fun getDataAyah() {
//        viewModel.getAyahBySura(requireContext(), sura.num.toString()).observe(viewLifecycleOwner, {
//            it.let { list -> adapter.updateList(list) }
//        })
    }

    private fun showMessage(message: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onStop() {
        super.onStop()
//        model.addLatestread(lastpageShown)
//        saveReadLog()
    }
}
