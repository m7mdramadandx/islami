package com.ramadan.islami.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.ramadan.islami.R
import com.ramadan.islami.ui.viewModel.ViewModel
import kotlinx.android.synthetic.main.send_feedback.*

class SendFeedback : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_feedback)
        sendBtn.setOnClickListener {
            if (msgEditText.text.toString().isNotEmpty())
                viewModel.sendFeedback(msgEditText.text.toString())
        }
    }
}