package com.applover.ext

import android.util.Patterns
import android.view.View
import com.applover.common.EMPTY_FIELD
import com.applover.common.WRONG_EMAIL_PATTERN
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

val String.Companion.EMPTY
    get() = ""

fun String.isEmail() : Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()
