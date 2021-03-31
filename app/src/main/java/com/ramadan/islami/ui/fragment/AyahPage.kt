package com.ramadan.islami.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Surah
import com.ramadan.islami.ui.activity.MainActivity
import com.ramadan.islami.ui.adapter.QuranAdapter
import com.ramadan.islami.utils.LocaleHelper
import com.ramadan.islami.utils.nf
import kotlinx.android.synthetic.main.fragment_ayah.*
import kotlinx.android.synthetic.main.main_content.*

class AyahPage : Fragment() {
    private lateinit var surah: Surah
    private lateinit var viewPager: ViewPager2
    private lateinit var quranPageAdapter: QuranAdapter
    private lateinit var localeHelper: LocaleHelper
    private var pageNumber: Int = 0


    private var pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            pageNumber = surah.ayahs.first().page + position
            Toast.makeText(
                requireContext(),
                "بارك ٱللَّهُ فيك, اذكر ٱللَّهِ",
                Toast.LENGTH_SHORT
            ).show()
        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let { surah = AyahPageArgs.fromBundle(it).surah!! }
        @Suppress("DEPRECATION")
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        (activity as MainActivity).supportActionBar?.hide()
        (activity as MainActivity).fixedBanner.visibility = View.GONE
        (activity as MainActivity).constraintLayout.updatePadding(0, 0, 0, 0)
        quranPageAdapter = QuranAdapter()
        localeHelper = LocaleHelper()
    }

    override fun onDetach() {
        super.onDetach()
        @Suppress("DEPRECATION")
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        (activity as MainActivity).supportActionBar?.show()
        (activity as MainActivity).fixedBanner.visibility = View.VISIBLE
        (activity as MainActivity).loadAds()
    }

    override fun onPause() {
        super.onPause()
        showDialog(requireContext())
    }

    override fun onResume() {
        super.onResume()
        MainActivity.firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "AyahPage")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_ayah, container, false)
        viewPager = root.findViewById(R.id.doppelgangerViewPager)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quranPageAdapter.setAyahDataList(surah)
        viewPager.adapter = quranPageAdapter
        viewPager.registerOnPageChangeCallback(pageChangeCallback)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (localeHelper.getQuranMark(requireContext()) ==
                "${surah.name} - صفحة رقم ${surah.ayahs.first().page + position}"
            ) {
                tab.setIcon(R.drawable.ic_bookmark)
            }
            tab.text = java.lang.String.valueOf(nf.format(surah.ayahs.first().page + position))
        }.attach()
        viewPager.layoutDirection = ViewPager2.LAYOUT_DIRECTION_RTL
        tabLayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }

    private fun showDialog(ctx: Context) {
        val dialogBuilder = AlertDialog.Builder(ctx)
        val view = LayoutInflater.from(ctx).inflate(R.layout.alert_dialog_read_marker, null)
        dialogBuilder.setView(view)
        val alertDialog = dialogBuilder.create()
        alertDialog.window!!.attributes.windowAnimations = R.style.ShrinkAnimation
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        view.findViewById<TextView>(R.id.yes).setOnClickListener {
            localeHelper.setQuranMark(
                ctx,
                "${surah.name} - صفحة رقم $pageNumber"
            )
            alertDialog.dismiss()
        }
        view.findViewById<TextView>(R.id.notYet).setOnClickListener { alertDialog.dismiss() }
    }

}
