package com.ramadan.islami.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.R
import com.ramadan.islami.ui.activity.MainActivity

class FamilyTreeDetails : Fragment() {
    private var familyTree = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let { familyTree = FamilyTreeDetailsArgs.fromBundle(it).toString() }
    }

    override fun onResume() {
        super.onResume()
        MainActivity.firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "FamilyTreeDetails")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.family_tree, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.familyTreeImage).apply {
            setImageDrawable(resources.getDrawable(R.drawable.prophets_tree))
        }
    }
}