
package com.example.skincareapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.annotation.DrawableRes
import com.example.skincareapp.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skincareapp.model.Product
import com.example.skincareapp.viewmodel.ProductViewModel

private val Green = Color(0xFF238367)
private val Cream = Color(0xFFFAFAF5)
private val TextDark = Color(0xFF222222)

@Composable
fun ProductsScreen(
    viewModel: ProductViewModel,
    onBack: () -> Unit = {},
    onProductClick: (Int) -> Unit = {}
) {
    val products by viewModel.products.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedProduct by remember { mutableStateOf<String?>(null) }

    val filteredProducts = products.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.brand.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        containerColor = Cream,
        topBar = {
            Surface(color = Color(0xFFF0F0EA)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .height(60.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Green
                        )
                    }

                    Text(
                        text = "Products",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Your product shelf",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Search products")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Green,
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "${filteredProducts.size} products · Tap to review",
                fontSize = 13.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (selectedProduct != null) {
                Text(
                    text = "Selected: $selectedProduct",
                    color = Green,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            if (filteredProducts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No products found",
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(
                        items = filteredProducts,
                        key = { it.id }
                    ) { product ->

                        ProductsListCard(
                            product = product,
                            onClick = {
                                selectedProduct = product.name
                                onProductClick(product.id)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun ProductsListCard(
    product: Product,
    onClick: () -> Unit
) {
    @DrawableRes
    val imageRes = when (product.id) {
        1 -> R.drawable.camomile
        2 -> R.drawable.arencia
        3 -> R.drawable.unove
        4 -> R.drawable.aestura
        else -> R.drawable.camomile
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(85.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.brand.uppercase(),
                    fontSize = 11.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextDark,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (product.ingredients.isNotEmpty()) {
                        "${product.ingredients.size} ingredients · Review"
                    } else {
                        "Ingredient list not reviewed"
                    },
                    fontSize = 12.sp,
                    color = Green
                )
            }
        }
    }
}
