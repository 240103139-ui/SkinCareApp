
package com.example.skincareapp.view

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

private val Green = Color(0xFF238367)
private val Cream = Color(0xFFFAFAF5)
private val LightGreen = Color(0xFFDCEBDD)

@Composable
fun ScannerScreen(
    onBack: () -> Unit
) {
    var scanMode by remember { mutableStateOf("Ingredient list") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var cameraPhoto by remember { mutableStateOf<Bitmap?>(null) }
    var manualText by remember { mutableStateOf("") }
    var showManualInput by remember { mutableStateOf(false) }
    var ingredients by remember {
        mutableStateOf<List<String>>(emptyList())
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            photoUri = uri
            cameraPhoto = null
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            cameraPhoto = bitmap
            photoUri = null
        }
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
                        "Scan label",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
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
                "Know what's inside.",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Capture a cosmetic label or enter its " +
                        "ingredients manually.",
                color = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(310.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LightGreen
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        cameraPhoto != null -> {
                            Image(
                                bitmap = cameraPhoto!!.asImageBitmap(),
                                contentDescription = "Camera photo",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        photoUri != null -> {
                            AndroidView(
                                factory = { context ->
                                    ImageView(context).apply {
                                        scaleType =
                                            ImageView.ScaleType.FIT_CENTER
                                    }
                                },
                                update = { imageView ->
                                    imageView.setImageURI(photoUri)
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        else -> {
                            Column(
                                horizontalAlignment =
                                    Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.CameraAlt,
                                    contentDescription = null,
                                    modifier = Modifier.size(65.dp),
                                    tint = Green
                                )

                                Spacer(Modifier.height(18.dp))

                                Text(
                                    "No label in view",
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(Modifier.height(10.dp))

                                Text(
                                    "Take a photo or upload " +
                                            "an ingredient label.",
                                    fontSize = 13.sp,
                                    color = Color.DarkGray,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(18.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("Ingredient list", "Barcode").forEach { mode ->
                    FilterChip(
                        selected = scanMode == mode,
                        onClick = { scanMode = mode },
                        label = { Text(mode) },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Green,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            if (scanMode == "Barcode") {
                Text(
                    "Barcode recognition is not available yet.",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Spacer(Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = {
                        galleryLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        Icons.Default.PhotoLibrary,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(6.dp))
                    Text("Upload")
                }

                Button(
                    onClick = {
                        cameraLauncher.launch(null)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(6.dp))
                    Text("Camera")
                }
            }

            Spacer(Modifier.height(20.dp))

            TextButton(
                onClick = {
                    showManualInput = !showManualInput
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Enter ingredients manually",
                    color = Green,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (showManualInput) {
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = manualText,
                    onValueChange = {
                        manualText = it
                        ingredients = emptyList()
                    },
                    label = { Text("Ingredients") },
                    placeholder = {
                        Text("Water, Glycerin, Linalool")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        ingredients = manualText
                            .split(",", "\n")
                            .map { it.trim() }
                            .filter { it.isNotEmpty() }
                    },
                    enabled = manualText.isNotBlank(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green
                    )
                ) {
                    Text("Review ingredients")
                }
            }

            if (ingredients.isNotEmpty()) {
                Spacer(Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Green
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        "${ingredients.size} ingredients entered",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(12.dp))

                ingredients.forEach { ingredient ->
                    Text(
                        "• $ingredient",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}
