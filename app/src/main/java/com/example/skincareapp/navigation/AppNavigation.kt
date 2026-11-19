
package com.example.skincareapp.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skincareapp.model.IngredientDatabase
import com.example.skincareapp.view.HomeScreen
import com.example.skincareapp.view.ProductsScreen
import com.example.skincareapp.view.ScanResultsScreen
import com.example.skincareapp.viewmodel.SkinProfileViewModel
import com.example.skincareapp.view.SkinProfileScreen
import com.example.skincareapp.view.IngredientDetailsScreen
import com.example.skincareapp.viewmodel.ProductViewModel
import com.example.skincareapp.view.ScannerScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val productViewModel = remember { ProductViewModel() }
    val skinProfileViewModel = remember {
        SkinProfileViewModel()
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("scanner") {
            ScannerScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("home") {
            HomeScreen(
                onProductsClick = {
                    navController.navigate("products")
                },
                onProfileClick = {
                    navController.navigate("skin_profile")
                },
                onScanClick = {
                    navController.navigate("scanner")
                }
            )
        }

        composable("products") {
            ProductsScreen(
                viewModel = productViewModel,
                onBack = {
                    navController.popBackStack()
                },
                onProductClick = { productId ->
                    navController.navigate("scan_results/$productId")
                }
            )
        }

        composable("skin_profile") {
            SkinProfileScreen(
                viewModel = skinProfileViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "scan_results/{productId}",
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            )
        ) { entry ->

            val productId = entry.arguments
                ?.getInt("productId") ?: -1

            ScanResultsScreen(
                product = productViewModel.getProductById(productId),
                onBack = {
                    navController.popBackStack()
                },
                onIngredientClick = { name ->
                    navController.navigate(
                        "ingredient_details/${Uri.encode(name)}"
                    )
                }
            )
        }

        composable(
            route = "ingredient_details/{ingredientName}",
            arguments = listOf(
                navArgument("ingredientName") {
                    type = NavType.StringType
                }
            )
        ) { entry ->

            val ingredientName = entry.arguments
                ?.getString("ingredientName")
                .orEmpty()

            IngredientDetailsScreen(
                ingredient = IngredientDatabase.findByName(ingredientName),
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
