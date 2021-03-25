package com.ramadan.islami.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ramadan.islami.R
import com.ramadan.islami.data.listener.FirebaseListener
import com.ramadan.islami.ui.viewModel.FirebaseViewModel

class Feedback : Fragment(), FirebaseListener {
    private val viewModel by lazy { ViewModelProvider(this).get(FirebaseViewModel::class.java) }
    private lateinit var sendBtn: Button
    private lateinit var msgEditText: EditText
    private lateinit var progress: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_feedback, container, false)
        viewModel.firebaseListener = this
        sendBtn = root.findViewById(R.id.sendBtn)
        msgEditText = root.findViewById(R.id.msgEditText)
        progress = root.findViewById(R.id.progress)
        sendBtn.setOnClickListener {
            if (msgEditText.text.toString().isNotEmpty())
                viewModel.sendFeedback(msgEditText.text.toString())
            else
                onFailure(getString(R.string.noWordsToSend))
        }
        return root
    }

    override fun onStarted() {
        progress.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progress.visibility = View.GONE
        Toast.makeText(context, getString(R.string.thanks_for_your_time), Toast.LENGTH_LONG).show()
        msgEditText.text.clear()
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}