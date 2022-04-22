package com.example.codechallengedrop.extension

import com.example.codechallengedrop.ui.BalanceFragment

fun Float.percentToValue(): Int {
    val result = (this / 100) * BalanceFragment.WEIGHT_MAX
    return result.toInt()
}
