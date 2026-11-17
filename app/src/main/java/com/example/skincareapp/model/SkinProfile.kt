package com.example.skincareapp.model

data class SkinProfile(
    val name: String = "My everyday profile",
    val skinType: String = "Combination",
    val ingredientsToAvoid: List<String> = listOf("Fragrance")
)