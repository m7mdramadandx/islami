package com.ramadan.islami.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ramadan.islami.R
import com.ramadan.islami.ui.viewModel.Listener
import com.ramadan.islami.ui.viewModel.ViewModel
import kotlinx.android.synthetic.main.send_feedback.*

class SendFeedback : AppCompatActivity(), Listener {
    private val viewModel by lazy { ViewModelProvider(this).get(ViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_feedback)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ViewModel.listener = this
        sendBtn.setOnClickListener {
            if (msgEditText.text.toString().isNotEmpty())
                viewModel.sendFeedback(msgEditText.text.toString())
            else
                onFailure(getString(R.string.no_words_to_send))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStarted() {
        progress.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progress.visibility = View.GONE
        Toast.makeText(this, getString(R.string.thanks_for_your_time), Toast.LENGTH_LONG).show()
        msgEditText.text.clear()
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}