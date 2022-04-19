package com.example.codechallengedrop.data.response

data class Method(
    val mash_temp: List<MashTemp>,
    val fermentation: Fermentation,
    val twist: String? = null
) {
    fun getNewFormat(): MutableList<String> {
        val new_mash_temp = this.mash_temp.map { map ->
            "Mash Temp: ${map.temp.unit} ${map.temp.value}" +
                    if (map.duration != null) {
                        "\nDuration: ${map.duration}"
                    } else {
                        ""
                    }
        }.toMutableList()
        new_mash_temp.add("Fermentation: ${this.fermentation.temp.value} ${this.fermentation.temp.unit}")
        this.twist?.let {
            new_mash_temp.add("Twist: ${this.twist}")
        }
        return new_mash_temp
    }
}
