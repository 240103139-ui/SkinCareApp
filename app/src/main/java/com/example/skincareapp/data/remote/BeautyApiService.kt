package com.example.skincareapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface BeautyApiService {

    @GET("api/v2/search")
    suspend fun getProducts(
        @Query("categories_tags")
        category: String = "en:skin-care",

        @Query("page_size")
        pageSize: Int = 20,

        @Query("fields")
        fields: String =
            "code,product_name,brands,image_front_url,ingredients_text"
    ): BeautySearchResponse
}