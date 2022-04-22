package com.example.codechallengedrop.extension

import com.example.codechallengedrop.ui.BalanceFragment

fun Int.valueToPercente(): Float {
    return (this / BalanceFragment.WEIGHT_MAX.toFloat()) * 100
}
