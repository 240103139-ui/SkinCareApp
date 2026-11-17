package com.example.skincareapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import com.example.skincareapp.model.Product

class ProductViewModel : ViewModel() {

    private val _products = MutableStateFlow(
        listOf(
            Product(
                id = 1,
                name = "Camomile Silky Cleansing Oil",
                brand = "The Body Shop",
                ingredients = listOf(
                    "Glycine Soja Oil",
                    "Prunus Amygdalus Dulcis Oil",
                    "Helianthus Annuus Seed Oil",
                    "C12-15 Alkyl Benzoate",
                    "Linalool"
                )
            ),
            Product(
                id = 2,
                name = "Vitamin C Booster",
                brand = "Arencia",
                ingredients = emptyList()
            ),
            Product(
                id = 3,
                name = "Deep Damage Repair",
                brand = "UNOVE",
                ingredients = emptyList()
            ),
            Product(
                id = 4,
                name = "ATOBARRIER365",
                brand = "AESTURA",
                ingredients = emptyList()
            )
        )
    )

    val products: StateFlow<List<Product>> = _products

    fun getProductById(id: Int): Product? {
        return _products.value.find { it.id == id }
    }
}