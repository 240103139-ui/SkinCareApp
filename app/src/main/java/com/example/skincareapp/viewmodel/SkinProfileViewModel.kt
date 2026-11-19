
package com.example.skincareapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.skincareapp.model.SkinProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SkinProfileViewModel : ViewModel() {

    private val _profile = MutableStateFlow(SkinProfile())
    val profile = _profile.asStateFlow()

    private val _saved = MutableStateFlow(false)
    val saved = _saved.asStateFlow()

    fun updateName(name: String) {
        _profile.value = _profile.value.copy(name = name)
        _saved.value = false
    }

    fun updateSkinType(type: String) {
        _profile.value = _profile.value.copy(skinType = type)
        _saved.value = false
    }

    fun toggleIngredient(ingredient: String) {
        val current = _profile.value.ingredientsToAvoid

        val updated = if (ingredient in current) {
            current - ingredient
        } else {
            current + ingredient
        }

        _profile.value = _profile.value.copy(
            ingredientsToAvoid = updated
        )

        _saved.value = false
    }

    fun saveProfile() {
        _saved.value = true
    }
}
