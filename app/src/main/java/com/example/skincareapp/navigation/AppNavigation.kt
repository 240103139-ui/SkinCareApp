
package com.example.skincareapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skincareapp.view.HomeScreen
import com.example.skincareapp.view.ProductsScreen
import com.example.skincareapp.view.ScanResultsScreen
import com.example.skincareapp.viewmodel.ProductViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val productViewModel = remember { ProductViewModel() }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(
                onProductsClick = {
                    navController.navigate(Screen.Products.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Products.route) {
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

        composable(
            route = Screen.ScanResults.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val productId = backStackEntry.arguments
                ?.getInt("productId") ?: -1

            val product = productViewModel.getProductById(productId)

            ScanResultsScreen(
                product = product,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
