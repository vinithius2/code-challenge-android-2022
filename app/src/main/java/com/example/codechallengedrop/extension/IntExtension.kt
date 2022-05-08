package com.example.codechallengedrop.extension

import com.example.codechallengedrop.ui.BeerViewModel

/**
 * Get percent value about max value permit and convert Int to Float.
 */
fun Int.valueToPercente(): Float {
    return (this / BeerViewModel.WEIGHT_MAX.toFloat()) * 100
}
