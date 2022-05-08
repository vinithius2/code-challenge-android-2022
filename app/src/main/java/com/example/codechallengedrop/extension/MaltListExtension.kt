package com.example.codechallengedrop.extension

import com.example.codechallengedrop.data.response.DefaultValueUnit
import com.example.codechallengedrop.data.response.Malt

/**
 * Format List<Malt> to MutableList<Pair<String, DefaultValueUnit>> using 'name', 'value' and 'unit'
 * for create a only String.
 */
fun List<Malt>.getNewFormat(): MutableList<Pair<String, DefaultValueUnit>> {
    return this.map {
        val value = "${it.name} (${it.amount.value} ${it.amount.unit})"
        Pair(value, it.amount)
    }.toMutableList()
}
