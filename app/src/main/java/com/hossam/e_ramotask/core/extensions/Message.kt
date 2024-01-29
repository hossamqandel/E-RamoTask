package com.hossam.e_ramotask.core.extensions

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackbar(message: String){
    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
}

fun Fragment.showSnackbar(@StringRes message: Int){
    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
}