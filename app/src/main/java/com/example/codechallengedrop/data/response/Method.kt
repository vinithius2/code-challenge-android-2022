package com.example.codechallengedrop.data.response

data class Method(
    val mash_temp: List<MashTemp>,
    val fermentation: Fermentation,
    val twist: String? = null
)
