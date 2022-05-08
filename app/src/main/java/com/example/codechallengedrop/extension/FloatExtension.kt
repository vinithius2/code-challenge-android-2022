package com.example.codechallengedrop.extension

import com.example.codechallengedrop.ui.BeerViewModel

/**
 * Convert Float to Int in Percent.
 */
fun Float.percentToValue(): Int {
    val result = (this / 100) * BeerViewModel.WEIGHT_MAX
    return result.toInt()
}
