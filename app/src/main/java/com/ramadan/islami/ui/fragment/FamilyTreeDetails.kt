package com.ramadan.islami.ui.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.R
import com.ramadan.islami.ui.activity.MainActivity
import com.ramadan.islami.utils.*
import com.squareup.picasso.Picasso

class FamilyTreeDetails : Fragment() {
    private var familyTree = ""
    private lateinit var imageView: ImageView
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
        val root = inflater.inflate(R.layout.fragment_family_tree, container, false)
        imageView = root.findViewById(R.id.familyTreeImage)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (MainActivity.language == "ar") {
            view.findViewById<ImageView>(R.id.familyTreeImage).apply {
                when {
                    familyTree.contains("muhammad") -> {
                        Picasso.get().load(muhammadFamilyAR)
                            .error(R.drawable.failure_img)
                            .placeholder(R.drawable.load_img).into(imageView)
                    }
                    familyTree.contains("prophets") -> {
                        Picasso.get().load(prophetsFamilyAR).error(R.drawable.failure_img)
                            .placeholder(R.drawable.load_img).into(imageView)
                    }
                    else -> {
                        Picasso.get().load(prophetsFamilyAR).error(R.drawable.failure_img)
                            .placeholder(R.drawable.load_img).into(imageView)
                    }
                }
            }
        } else {
            view.findViewById<ImageView>(R.id.familyTreeImage).apply {
                when {
                    familyTree.contains("muhammad") -> {
                        Picasso.get().load(muhammadFamilyEN)
                            .error(R.drawable.failure_img)
                            .placeholder(R.drawable.load_img).into(imageView)
                    }
                    familyTree.contains("prophets") -> {
                        Picasso.get().load(prophetsFamilyEN).error(R.drawable.failure_img)
                            .placeholder(R.drawable.load_img).into(imageView)
                    }
                    else -> {
                        Picasso.get().load(prophetsFamilyEN).error(R.drawable.failure_img)
                            .placeholder(R.drawable.load_img).into(imageView)
                    }
                }
            }
        }
        imageView.setOnLongClickListener {
            var bitmap: Bitmap? = null
            try {
                bitmap = (imageView.drawable as BitmapDrawable).bitmap
            } catch (e: Exception) {
                Log.e("ERROR", e.localizedMessage!!)
            }
            try {
                saveImage(bitmap!!)
                it.snackBar(view.context.getString(R.string.saved))
            } catch (e: Exception) {
                it.snackBar(view.context.getString(R.string.failedToDownload))
            }
            false
        }

    }
}