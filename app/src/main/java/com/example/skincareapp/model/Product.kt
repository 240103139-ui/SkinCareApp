package com.example.skincareapp.model

data class Product(
    val id: Int,
    val name: String,
    val brand: String,
    val ingredients: List<String>
)