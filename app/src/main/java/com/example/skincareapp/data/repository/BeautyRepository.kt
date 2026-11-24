package com.example.skincareapp.data.repository

import com.example.skincareapp.data.remote.BeautyProductDto
import com.example.skincareapp.data.remote.BeautyApiService
import com.example.skincareapp.data.remote.RetrofitClient

class BeautyRepository(
    private val api: BeautyApiService = RetrofitClient.api
) {

    suspend fun getSkinCareProducts(): List<BeautyProductDto> {
        val response = api.getProducts()

        return response.products.orEmpty()
    }
}