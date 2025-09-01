package com.juanpablo0612.agrostaff.utils

fun validateEmail(email: String): Boolean {
    val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    return emailRegex.matches(email)
}

fun validatePassword(password: String): Boolean {
    return password.length >= 8
}