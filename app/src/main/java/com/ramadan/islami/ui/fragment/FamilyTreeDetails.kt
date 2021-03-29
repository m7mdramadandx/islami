package com.ramadan.islami.ui.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.R
import com.ramadan.islami.ui.activity.MainActivity
import com.ramadan.islami.utils.*
import com.squareup.picasso.Picasso
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuGravity
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams

class FamilyTreeDetails : Fragment() {
    private var familyTree = ""
    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment
    private lateinit var imageView: ImageView
    private lateinit var imageUrl: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let { familyTree = FamilyTreeDetailsArgs.fromBundle(it).toString() }
        initMenuFragment()
    }

    override fun onResume() {
        super.onResume()
        MainActivity.firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
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
        setHasOptionsMenu(true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (MainActivity.language == "ar") {
            view.findViewById<ImageView>(R.id.familyTreeImage).apply {
                imageUrl = when {
                    familyTree.contains("muhammad") -> muhammadFamilyAR
                    familyTree.contains("prophets") -> prophetsFamilyAR
                    else -> prophetsFamilyAR
                }
            }
        } else {
            view.findViewById<ImageView>(R.id.familyTreeImage).apply {
                imageUrl = when {
                    familyTree.contains("muhammad") -> muhammadFamilyEN
                    familyTree.contains("prophets") -> prophetsFamilyEN
                    else -> prophetsFamilyEN
                }
            }
        }
        Picasso.get().load(imageUrl).error(R.drawable.failure_img)
            .placeholder(R.drawable.load_img).into(imageView)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.let { if (it.itemId == R.id.context_menu) showContextMenuDialogFragment() }
        return super.onOptionsItemSelected(item)
    }

    private fun initMenuFragment() {
        val menuParams = MenuParams(
            actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
            menuObjects = getMenuObjects(),
            isClosableOutside = true,
            gravity = MenuGravity.END
        )
        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
            menuItemClickListener = { view, position ->
                when (position) {
                    0 -> {
                        val drawable = imageView.drawable
                        val bitmap: Bitmap? = if (drawable is BitmapDrawable) {
                            (imageView.drawable as BitmapDrawable).bitmap
                        } else null
                        bitmap?.let { it1 ->
                            saveImage(it1)
                            showMessage(view.context, view.context.getString(R.string.saved))
                        } ?: showMessage(view.context,
                            view.context.getString(R.string.failedToDownload))

                    }
                    1 -> {
                        val bmpUri: Uri = getLocalBitmapUri(imageView)!!
                        val shareIntent = Intent()
                        shareIntent.action = Intent.ACTION_SEND
                        shareIntent.type = "picture/png"
                        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri)
                        view.context.startActivity(Intent.createChooser(shareIntent, "Send to"))
                    }
                }
            }
        }
    }

    private fun getMenuObjects() = mutableListOf<MenuObject>().apply {
        MenuObject(getString(R.string.download)).apply {
            setResourceValue(R.drawable.ic_download)
            setBgColorValue((Color.rgb(23, 34, 59)))
            add(this)
        }
        MenuObject(getString(R.string.share)).apply {
            setResourceValue(R.drawable.ic_share)
            setBgColorValue((Color.rgb(22, 36, 71)))
            add(this)
        }
    }

    private fun showContextMenuDialogFragment() {
        @Suppress("DEPRECATION")
        if (requireFragmentManager().findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(requireFragmentManager(), ContextMenuDialogFragment.TAG)
        }
    }
}