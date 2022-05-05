package com.example.codechallengedrop.extension

import com.example.codechallengedrop.data.response.DefaultValueUnit
import com.example.codechallengedrop.data.response.Hops

fun List<Hops>.getNewFormat(): MutableList<Pair<String, DefaultValueUnit>> {
    return this.map {
        val value = "${it.name} (${it.amount.value} ${it.amount.unit})"
        Pair(value, it.amount)
    }.toMutableList()
}
