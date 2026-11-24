package com.example.skincareapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skincareapp.data.remote.BeautyProductDto
import com.example.skincareapp.data.repository.BeautyRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BeautyProductsUiState {
    data object Loading : BeautyProductsUiState

    data class Success(
        val products: List<BeautyProductDto>
    ) : BeautyProductsUiState

    data object Empty : BeautyProductsUiState

    data class Error(
        val message: String
    ) : BeautyProductsUiState
}

class BeautyProductsViewModel(
    private val repository: BeautyRepository = BeautyRepository()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<BeautyProductsUiState>(
            BeautyProductsUiState.Loading
        )

    val uiState: StateFlow<BeautyProductsUiState> =
        _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = BeautyProductsUiState.Loading

            try {
                val products = repository.getSkinCareProducts()

                _uiState.value = if (products.isEmpty()) {
                    BeautyProductsUiState.Empty
                } else {
                    BeautyProductsUiState.Success(products)
                }

            } catch (error: CancellationException) {
                throw error

            } catch (error: IOException) {
                _uiState.value = BeautyProductsUiState.Error(
                    "Please check your internet connection."
                )

            } catch (error: HttpException) {
                _uiState.value = BeautyProductsUiState.Error(
                    "Server error: ${error.code()}"
                )

            } catch (error: Exception) {
                _uiState.value = BeautyProductsUiState.Error(
                    error.message ?: "Something went wrong."
                )
            }
        }
    }
}