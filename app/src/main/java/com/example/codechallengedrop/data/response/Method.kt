package com.example.codechallengedrop.data.response

data class Method(
    val mash_temp: List<MashTemp>,
    val fermentation: Fermentation,
    val twist: String? = null,
    val status: Boolean = false
) {
    fun getNewFormat(): MutableList<Pair<String, DefaultValueUnit>> {
        val newMashTemp = this.mash_temp.map { map ->
            Pair(getFormatMashTemp(map), map.temp)
        }.toMutableList()
        newMashTemp.add(
            Pair(
                "Fermentation: ${this.fermentation.temp.value} ${this.fermentation.temp.unit}",
                this.fermentation.temp
            )
        )
        this.twist?.let {
            newMashTemp.add(Pair("Twist: $it", DefaultValueUnit()))
        }
        return newMashTemp
    }

    private fun getFormatMashTemp(mash_temp: MashTemp): String {
        return "Mash Temp: ${mash_temp.temp.unit} ${mash_temp.temp.value}" +
                if (mash_temp.duration != null) {
                    "\nDuration: ${mash_temp.duration}"
                } else {
                    ""
                }
    }
}
