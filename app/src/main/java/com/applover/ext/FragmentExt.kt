package com.applover.ext

import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

fun Fragment.onBackPressedNavigateTo(@IdRes action: Int) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
        findNavController().navigate(action)
    }
}

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(requireContext(), text, duration).show()

fun Fragment.snackBar(text: String, duration: Int = Snackbar.LENGTH_SHORT) = Snackbar.make(requireView(), text, duration).show()