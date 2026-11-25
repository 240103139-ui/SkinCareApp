package com.example.skincareapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skincareapp.data.preferences.HistoryPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryPreferencesViewModel(
    private val repository: HistoryPreferencesRepository
) : ViewModel() {

    val newestFirst: StateFlow<Boolean> =
        repository.newestFirst.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = true
        )

    fun setNewestFirst(value: Boolean) {
        viewModelScope.launch {
            repository.setNewestFirst(value)
        }
    }
}