package com.example.skincareapp.data.remote

import com.google.gson.annotations.SerializedName

data class BeautyProductDto(
    val code: String?,

    @SerializedName("product_name")
    val productName: String?,

    val brands: String?,

    @SerializedName("image_front_url")
    val imageUrl: String?,

    @SerializedName("ingredients_text")
    val ingredientsText: String?
)

data class BeautySearchResponse(
    val products: List<BeautyProductDto>?
)