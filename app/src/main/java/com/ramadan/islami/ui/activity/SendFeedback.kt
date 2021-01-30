package com.ramadan.islami.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.ramadan.islami.R
import com.ramadan.islami.ui.viewModel.Listener
import com.ramadan.islami.ui.viewModel.ViewModel
import kotlinx.android.synthetic.main.send_feedback.*

class SendFeedback : AppCompatActivity(), Listener {
    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_feedback)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.listener = this
        sendBtn.setOnClickListener {
            if (msgEditText.text.toString().isNotEmpty())
                viewModel.sendFeedback(msgEditText.text.toString())
            else
                onFailure("No words to send")
        }
    }

    override fun onStarted() {
        progress.visibility = View.VISIBLE

    }

    override fun onSuccess() {
        progress.visibility = View.GONE
        Toast.makeText(this, "Thanks for your time", Toast.LENGTH_LONG).show()
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}