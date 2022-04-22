package com.example.codechallengedrop.data.response

data class Hops(
    val name: String,
    val amount: DefaultValueUnit,
    val add: String,
    val attribute: String,
    var status: Boolean = false
)
