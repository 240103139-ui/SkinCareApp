package com.example.skincareapp.model

data class ScanRecord(
    val productId: Int,
    val savedAt: Long = System.currentTimeMillis(),
    val note: String = ""
)
