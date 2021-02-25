package com.maciel.murillo.finance_manager.extensions

import android.util.Base64
import java.util.regex.Pattern

fun String.toBase64() = Base64
    .encodeToString(this.encodeToByteArray(), Base64.DEFAULT)
    .replace("(\\n|\\r )", "")

fun String.fromBase64() = String(Base64.decode(this, Base64.DEFAULT))

fun String.toChoosenDate(): String {
    val date = this.split("/")
    return date[1] + date [2]
}

fun String?.safe() = this ?: ""

fun String.isEmailValid(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}