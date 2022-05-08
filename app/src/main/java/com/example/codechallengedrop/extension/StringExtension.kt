package com.example.codechallengedrop.extension

/**
 * Make the first letter of the String upper case.
 */
fun String.capitalize(): String {
    return this.lowercase().replaceFirstChar(Char::uppercase)
}