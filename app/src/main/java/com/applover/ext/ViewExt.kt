package com.applover.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.applover.common.NO_FLAG
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, NO_FLAG)
}

fun TextInputEditText.checkDataValidationOnUnfocus(
    parentLayout: TextInputLayout,
    onBlank: ((TextInputLayout) -> Unit),
    onNonEmail: ((TextInputLayout) -> Unit)
) {
    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        val inputTextField = text.toString()
        if (!hasFocus) {
            when {
                inputTextField.isBlank() -> onBlank.invoke(parentLayout)
                !inputTextField.isEmail() -> onNonEmail.invoke(parentLayout)
                else -> parentLayout.error = null
            }
        } else {
            parentLayout.error = null
        }
    }
}
