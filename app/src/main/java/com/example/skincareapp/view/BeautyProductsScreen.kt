package com.example.skincareapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.skincareapp.data.remote.BeautyProductDto
import com.example.skincareapp.viewmodel.BeautyProductsUiState
import com.example.skincareapp.viewmodel.BeautyProductsViewModel

private val Green = Color(0xFF238367)
private val Cream = Color(0xFFFAFAF5)

@Composable
fun BeautyProductsScreen(
    viewModel: BeautyProductsViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Explore Beauty Products",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {

            BeautyProductsUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = Green)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Loading beauty products...")
                    }
                }
            }

            is BeautyProductsUiState.Success -> {
                Text(
                    text = "${state.products.size} products found",
                    color = Green,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(state.products) { product ->
                        BeautyProductCard(product)
                    }
                }
            }

            BeautyProductsUiState.Empty -> {
                BeautyMessageState(
                    title = "No products found",
                    description = "There are no products available right now.",
                    buttonText = "Try again",
                    onRetry = {
                        viewModel.loadProducts()
                    }
                )
            }

            is BeautyProductsUiState.Error -> {
                BeautyMessageState(
                    title = "Something went wrong",
                    description = state.message,
                    buttonText = "Retry",
                    onRetry = {
                        viewModel.loadProducts()
                    }
                )
            }
        }
    }
}

@Composable
private fun BeautyMessageState(
    title: String,
    description: String,
    buttonText: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(buttonText)
            }
        }
    }
}

@Composable
private fun BeautyProductCard(
    product: BeautyProductDto
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.brands
                    ?.ifBlank { "Unknown brand" }
                    ?: "Unknown brand",
                color = Green,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = product.productName
                    ?.ifBlank { "Unnamed product" }
                    ?: "Unnamed product",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.ingredientsText
                    ?.takeIf { it.isNotBlank() }
                    ?: "Ingredients not available",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                color = Color.DarkGray
            )
        }
    }
}