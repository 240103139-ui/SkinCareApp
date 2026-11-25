package com.example.skincareapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_scans")
data class SavedScanEntity(

    @PrimaryKey
    val productId: Int,

    val savedAt: Long = System.currentTimeMillis(),

    val note: String = ""
)