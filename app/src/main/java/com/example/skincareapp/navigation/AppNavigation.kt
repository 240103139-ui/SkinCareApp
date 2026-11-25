
package com.example.skincareapp.navigation

import android.net.Uri
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.skincareapp.view.ScanHistoryScreen
import com.example.skincareapp.viewmodel.ScanHistoryViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skincareapp.view.BeautyProductsScreen
import com.example.skincareapp.viewmodel.BeautyProductsViewModel


@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val productViewModel = remember { ProductViewModel() }
    val skinProfileViewModel = remember {
        SkinProfileViewModel()
    }
    val scanHistoryViewModel = remember {
        ScanHistoryViewModel()
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
        composable("api_products") {
            val beautyViewModel: BeautyProductsViewModel = viewModel()

            BeautyProductsScreen(
                viewModel = beautyViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("home") {
            HomeScreen(
                onProductsClick = {
                    navController.navigate("products") {
                        launchSingleTop = true
                    }
                },
                onProfileClick = {
                    navController.navigate("skin_profile") {
                        launchSingleTop = true
                    }
                },
                onScanClick = {
                    navController.navigate("scanner") {
                        launchSingleTop = true
                    }
                },
                onHistoryClick = {
                    navController.navigate("scan_history") {
                        launchSingleTop = true
                    }
                },
                onProductClick = { productId ->
                    navController.navigate(
                        "scan_results/$productId"
                    )
                },
                onApiProductsClick = {
                    navController.navigate("api_products") {
                        launchSingleTop = true
                    }
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

            val history by scanHistoryViewModel.history.collectAsState()

            ScanResultsScreen(
                product = productViewModel.getProductById(productId),
                onBack = {
                    navController.popBackStack()
                },
                onIngredientClick = { name ->
                    navController.navigate(
                        "ingredient_details/${Uri.encode(name)}"
                    )
                },
                onSave = { product ->
                    scanHistoryViewModel.saveScan(product)
                },
                isSaved = history.any {
                    it.productId == productId
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

        composable("scan_history") {
            ScanHistoryScreen(
                historyViewModel = scanHistoryViewModel,
                productViewModel = productViewModel,
                onBack = {
                    navController.popBackStack()
                },
                onProductClick = { productId ->
                    navController.navigate("scan_results/$productId")
                },
                onScanClick = {
                    navController.navigate("scanner")
                }
            )
        }

    }
}
