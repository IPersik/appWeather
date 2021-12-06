package com.example.weather.experiments

import android.content.Context
import java.text.SimpleDateFormat
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.util.*
import android.view.inputmethod.InputMethodManager

const val DATE_TIME_FORMAT = "dd.MMM.yy HH:mm"

fun Date.format(): String {
    return SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(this)
}

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE,
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) { }
    return false
}