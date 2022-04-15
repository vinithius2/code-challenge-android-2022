package com.example.codechallengedrop.data.response

data class Beer(
    val id: Int,
    val name: String,
    val tagline: String,
    val first_brewed: String,
    val description: String,
    val image_url: String,
    val abv: Double,
    val ibu: Int,
    val target_fg: Int,
    val target_og: Int,
    val ebc: Int,
    val srm: Int,
    val ph: Double,
    val attenuation_level: Int,
    val defaultValueUnit: DefaultValueUnit,
    val boil_defaultValueUnit: DefaultValueUnit,
    val method: Method,
    val ingredients: Ingredients,
    val food_pairing: List<String>,
    val brewers_tips: String,
    val contributed_by: String
)
