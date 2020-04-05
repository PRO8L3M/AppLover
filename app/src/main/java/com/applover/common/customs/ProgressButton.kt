package com.applover.common.customs

import android.view.View
import kotlinx.android.synthetic.main.progress_button_layout.view.progress_button_progress_bar
import kotlinx.android.synthetic.main.progress_button_layout.view.progress_button_text_view

class ProgressButton(view: View) {

    private val progressBar = view.progress_button_progress_bar
    private val textView = view.progress_button_text_view

    fun buttonActivated() {
        progressBar.visibility = View.VISIBLE
        textView.visibility = View.INVISIBLE
    }

    fun actionFinished() {
        progressBar.visibility = View.GONE
        textView.visibility = View.VISIBLE
    }
}