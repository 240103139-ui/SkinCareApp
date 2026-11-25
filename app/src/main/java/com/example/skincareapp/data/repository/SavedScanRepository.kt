package com.example.skincareapp.data.repository

import com.example.skincareapp.data.local.SavedScanDao
import com.example.skincareapp.data.local.SavedScanEntity
import kotlinx.coroutines.flow.Flow

class SavedScanRepository(
    private val dao: SavedScanDao
) {

    val allScans: Flow<List<SavedScanEntity>> =
        dao.getAllScans()

    suspend fun saveScan(productId: Int) {
        dao.insertScan(
            SavedScanEntity(productId = productId)
        )
    }

    suspend fun getScan(productId: Int): SavedScanEntity? {
        return dao.getScanById(productId)
    }

    suspend fun updateNote(productId: Int, note: String) {
        dao.updateNote(productId, note)
    }

    suspend fun deleteScan(productId: Int) {
        dao.deleteScan(productId)
    }

    suspend fun clearHistory() {
        dao.clearHistory()
    }
}