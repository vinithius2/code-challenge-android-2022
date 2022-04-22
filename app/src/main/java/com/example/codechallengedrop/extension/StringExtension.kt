package com.example.codechallengedrop.extension

fun String.capitalize(): String {
    return this.lowercase().replaceFirstChar(Char::uppercase)
}