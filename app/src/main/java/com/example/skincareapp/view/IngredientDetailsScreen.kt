
package com.example.skincareapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skincareapp.model.IngredientInfo

private val Green = Color(0xFF238367)
private val Cream = Color(0xFFFAFAF5)
private val Yellow = Color(0xFFF3F3B8)

@Composable
fun IngredientDetailsScreen(
    ingredient: IngredientInfo,
    onBack: () -> Unit
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
                        contentDescription = "Back",
                        tint = Green
                    )
                }

                Text(
                    "Ingredient details",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                ingredient.name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "COSMETIC INGREDIENT",
                fontSize = 11.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(16.dp))

            Text(
                ingredient.description,
                fontSize = 15.sp,
                lineHeight = 23.sp
            )

            Spacer(Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DetailCard(
                    title = "Function",
                    value = ingredient.function,
                    color = Color(0xFFE7DFF2),
                    modifier = Modifier.weight(1f)
                )

                DetailCard(
                    title = "Ingredient data",
                    value = "Demo information",
                    color = Color(0xFFDCEBDD),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(20.dp))

            if (ingredient.function.equals(
                    "Fragrance",
                    ignoreCase = true
                )
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Yellow
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Fragrance information",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            "Some fragrance ingredients may cause " +
                                    "irritation in sensitive individuals. " +
                                    "This does not confirm an allergy.",
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))
            }

            Text(
                "What to know",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Text(
                "The complete formula, concentration and " +
                        "individual skin response all matter. " +
                        "If irritation persists, seek qualified advice.",
                color = Color.DarkGray,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )

            Spacer(Modifier.height(24.dp))

            Text(
                "Scientific sources",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "European Commission - CosIng",
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "Ingredient functions and naming",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "Cosmetic Ingredient Review - CIR",
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "Safety assessment resources",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Back to scan results")
            }
        }
    }
}

@Composable
private fun DetailCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.heightIn(min = 110.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                title,
                fontSize = 12.sp,
                color = Color.DarkGray
            )

            Spacer(Modifier.height(12.dp))

            Text(
                value,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}
