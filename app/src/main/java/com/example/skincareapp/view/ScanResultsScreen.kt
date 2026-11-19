
package com.example.skincareapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skincareapp.R
import com.example.skincareapp.model.Product
import com.example.skincareapp.model.IngredientInfo
import com.example.skincareapp.model.IngredientDatabase

private val Green = Color(0xFF238367)
private val Cream = Color(0xFFFAFAF5)
private val LightGreen = Color(0xFFDCEBDD)
private val TextDark = Color(0xFF222222)

@Composable
fun ScanResultsScreen(
    product: Product?,
    onBack: () -> Unit,
    onIngredientClick: (String) -> Unit = {}
) {
    Scaffold(
        containerColor = Cream,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(60.dp)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text(
                    "Scan Results",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { padding ->

        if (product == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Product not found")
            }
        } else {
            val imageRes = when (product.id) {
                1 -> R.drawable.camomile
                2 -> R.drawable.arencia
                3 -> R.drawable.unove
                4 -> R.drawable.aestura
                else -> R.drawable.camomile
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Image(
                        painter = painterResource(imageRes),
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(18.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(Modifier.height(18.dp))

                Text(
                    product.brand.uppercase(),
                    color = Green,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    product.name,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30.sp,
                    color = TextDark
                )

                Spacer(Modifier.height(18.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = LightGreen
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = Green,
                            modifier = Modifier.size(28.dp)
                        )

                        Spacer(Modifier.width(14.dp))

                        Column {
                            Text(
                                "Ingredient Overview",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )

                            Text(
                                "${product.ingredients.size} ingredients in demo data",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    "Ingredient Breakdown",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(12.dp))

                if (product.ingredients.isEmpty()) {
                    Text(
                        "Ingredient data is not available yet.",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                } else {
                    product.ingredients.forEach { name ->
                        IngredientCard(
                            ingredient = IngredientDatabase.findByName(name),
                            onClick = {
                                onIngredientClick(name)
                            }
                        )

                        Spacer(Modifier.height(10.dp))
                    }
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    "Information is educational and does not replace professional advice.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun IngredientCard(
    ingredient: IngredientInfo,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                ingredient.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(Modifier.height(6.dp))

            Text(
                ingredient.function,
                fontSize = 12.sp,
                color = Green,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                ingredient.description,
                fontSize = 13.sp,
                lineHeight = 19.sp,
                color = Color.DarkGray
            )
        }
    }
}
