package com.example.skincareapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedScanDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertScan(scan: SavedScanEntity)

    @Query("SELECT * FROM saved_scans ORDER BY savedAt DESC")
    fun getAllScans(): Flow<List<SavedScanEntity>>

    @Query("SELECT * FROM saved_scans WHERE productId = :productId LIMIT 1")
    suspend fun getScanById(productId: Int): SavedScanEntity?

    @Query("UPDATE saved_scans SET note = :note WHERE productId = :productId")
    suspend fun updateNote(productId: Int, note: String)

    @Query("DELETE FROM saved_scans WHERE productId = :productId")
    suspend fun deleteScan(productId: Int)

    @Query("DELETE FROM saved_scans")
    suspend fun clearHistory()
}