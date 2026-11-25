package com.example.skincareapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skincareapp.data.repository.SavedScanRepository
import com.example.skincareapp.model.Product
import com.example.skincareapp.model.ScanRecord
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScanHistoryViewModel(
    private val repository: SavedScanRepository
) : ViewModel() {

    val history: StateFlow<List<ScanRecord>> =
        repository.allScans
            .map { scans ->
                scans.map { scan ->
                    ScanRecord(
                        productId = scan.productId,
                        savedAt = scan.savedAt,
                        note = scan.note
                    )
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList()
            )

    fun saveScan(product: Product) {
        viewModelScope.launch {
            repository.saveScan(product.id)
        }
    }

    fun isSaved(productId: Int): Boolean {
        return history.value.any { it.productId == productId }
    }

    fun removeScan(productId: Int) {
        viewModelScope.launch {
            repository.deleteScan(productId)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }

    fun updateNote(productId: Int, note: String) {
        viewModelScope.launch {
            repository.updateNote(productId, note.trim())
        }
    }
}
