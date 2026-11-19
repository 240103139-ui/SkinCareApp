
package com.example.skincareapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skincareapp.viewmodel.SkinProfileViewModel

private val Green = Color(0xFF238367)
private val Cream = Color(0xFFFAFAF5)
private val TextDark = Color(0xFF222222)

@Composable
fun SkinProfileScreen(
    viewModel: SkinProfileViewModel,
    onBack: () -> Unit
) {
    val profile by viewModel.profile.collectAsState()
    val saved by viewModel.saved.collectAsState()

    val skinTypes = listOf(
        "Oily", "Dry", "Combination", "Normal"
    )

    val avoidOptions = listOf(
        "Fragrance", "Alcohol", "Essential Oils"
    )

    Scaffold(
        containerColor = Cream,
        topBar = {
            Surface(color = Color(0xFFF0F0EA)) {
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
                        "Your skin profile",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(18.dp)
        ) {

            Text(
                "Care that fits you.",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Update your preferences to make " +
                        "ingredient guidance more personal.",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(26.dp))

            Text(
                "Profile name",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = profile.name,
                onValueChange = viewModel::updateName,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Green,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Only used to organize your skin preferences.",
                color = Color.Gray,
                fontSize = 12.sp
            )

            Spacer(Modifier.height(28.dp))
            HorizontalDivider(color = Color(0xFFE4E4DD))
            Spacer(Modifier.height(24.dp))

            Text(
                "Skin type",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Choose one. You can change this anytime.",
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(16.dp))

            skinTypes.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    row.forEach { type ->
                        SelectionButton(
                            text = type,
                            selected = profile.skinType == type,
                            onClick = {
                                viewModel.updateSkinType(type)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(18.dp))
            HorizontalDivider(color = Color(0xFFE4E4DD))
            Spacer(Modifier.height(24.dp))

            Text(
                "Ingredients to avoid",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Choose any preferences. This does " +
                        "not confirm an allergy.",
                fontSize = 13.sp,
                color = Color.Gray,
                lineHeight = 19.sp
            )

            Spacer(Modifier.height(16.dp))

            avoidOptions.forEach { option ->
                SelectionButton(
                    text = option,
                    selected = option in profile.ingredientsToAvoid,
                    onClick = {
                        viewModel.toggleIngredient(option)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(25.dp))

            Button(
                onClick = viewModel::saveProfile,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = profile.name.isNotBlank()
            ) {
                Text(
                    "Save Profile",
                    fontSize = 16.sp
                )
            }

            if (saved) {
                Spacer(Modifier.height(14.dp))

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFDCEBDD)
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = Green
                        )

                        Spacer(Modifier.width(10.dp))

                        Text(
                            "Profile saved in this session",
                            color = Green,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
private fun SelectionButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(25.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) Green else Color.White,
            contentColor = if (selected) Color.White else TextDark
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (selected) Green else Color.LightGray
        )
    ) {
        if (selected) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier.size(17.dp)
            )
            Spacer(Modifier.width(4.dp))
        }

        Text(
            text = text,
            fontSize = 13.sp,
            maxLines = 1
        )
    }
}
