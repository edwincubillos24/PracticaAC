package com.edwinacubillos.sesionroom

import android.content.Context
import android.widget.Toast
import java.util.regex.Matcher
import java.util.regex.Pattern

fun isEmailValid(email: String?): Boolean {
    val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(email)
    return matcher.matches()
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}


