package com.ramadan.islami.ui.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ramadan.islami.R
import kotlinx.android.synthetic.main.fragment_doppelganger.*
import java.io.IOException

class DoppelgangerFragment : Fragment() {

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val doppelgangerFragment = DoppelgangerFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            doppelgangerFragment.arguments = bundle
            return doppelgangerFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_doppelganger, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = requireArguments().getInt(ARG_POSITION)
////        val imageFilePath = getString(R.string.doppelganger_image_path, position)
////        val doppelgangerNamesArray =
////            requireContext().resources.getStringArray(R.array.doppelganger_names)
//
//        setImageFromAssetsFile(requireContext(), imageFilePath)
//        doppelgangerNameTv.text = doppelgangerNamesArray[position]
    }


    private fun setImageFromAssetsFile(context: Context, filePath: String) {
        val imageBitmap: Bitmap?
        val assets = context.resources.assets
        try {
            val fileStream = assets.open(filePath)
            imageBitmap = BitmapFactory.decodeStream(fileStream)
            fileStream.close()
            doppelgangerIv.setImageBitmap(imageBitmap)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        }
    }

}
