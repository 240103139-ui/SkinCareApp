package com.example.skincareapp.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Products : Screen("products")
    data object Scanner : Screen("scanner")
    data object ScanResults : Screen("scan_results/{productId}")
    data object IngredientDetails : Screen("ingredient_details/{ingredientName}")
    data object SkinProfile : Screen("skin_profile")
    data object ScanHistory : Screen("scan_history")
}