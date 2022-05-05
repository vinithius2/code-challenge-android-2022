package com.example.codechallengedrop.extension

import com.example.codechallengedrop.ui.BeerViewModel

fun Int.valueToPercente(): Float {
    return (this / BeerViewModel.WEIGHT_MAX.toFloat()) * 100
}
