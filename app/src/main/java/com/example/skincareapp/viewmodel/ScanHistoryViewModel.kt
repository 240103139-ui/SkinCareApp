
package com.example.skincareapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.skincareapp.model.Product
import com.example.skincareapp.model.ScanRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class ScanHistoryViewModel : ViewModel() {

    private val _history =
        MutableStateFlow<List<ScanRecord>>(emptyList())

    val history = _history.asStateFlow()

    fun saveScan(product: Product) {
        if (isSaved(product.id)) return

        val record = ScanRecord(productId = product.id)
        _history.value = listOf(record) + _history.value
    }

    fun isSaved(productId: Int): Boolean {
        return _history.value.any {
            it.productId == productId
        }
    }

    fun removeScan(productId: Int) {
        _history.value = _history.value.filter {
            it.productId != productId
        }
    }


    fun clearHistory() {
        _history.value = emptyList()
    }
}
