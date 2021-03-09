package com.ramadan.islami.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.ui.activity.MainActivity
import com.ramadan.islami.ui.adapter.QuranPageAdapter
import com.ramadan.islami.utils.nf
import kotlinx.android.synthetic.main.fragment_ayah.*
import kotlinx.android.synthetic.main.main_content.*

class AyahPage : Fragment() {

    //    private val viewModel by lazy { ViewModelProvider(this).get(QuranAyahViewModel::class.java) }
    private lateinit var surah: Surah
    private var toast: Toast? = null
    private lateinit var doppelgangerViewPager: ViewPager2
    private lateinit var quranPageAdapter: QuranPageAdapter

    private var doppelgangerPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            Toast.makeText(requireContext(),
                "Selected position: $position",
                Toast.LENGTH_SHORT).show()
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let { surah = AyahPageArgs.fromBundle(it).surah }
        (activity as MainActivity).supportActionBar?.hide()
        (activity as MainActivity).fixedBanner.removeAllViewsInLayout()
        quranPageAdapter = QuranPageAdapter()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).supportActionBar?.show()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_ayah, container, false)
        doppelgangerViewPager = root.findViewById(R.id.doppelgangerViewPager)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        quranPageAdapter.setAyahDataList(surah)
        doppelgangerViewPager.adapter = quranPageAdapter
        doppelgangerViewPager.registerOnPageChangeCallback(doppelgangerPageChangeCallback)
        TabLayoutMediator(tabLayout, doppelgangerViewPager) { tab, position ->
            tab.text = java.lang.String.valueOf(nf.format(position + 1))
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
