package com.example.skincareapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.skincareapp.model.ScanRecord
import com.example.skincareapp.viewmodel.ProductViewModel
import com.example.skincareapp.viewmodel.ScanHistoryViewModel
import java.text.DateFormat
import java.util.Date
import com.example.skincareapp.viewmodel.HistoryPreferencesViewModel

private val Green = Color(0xFF238367)
private val Cream = Color(0xFFFAFAF5)
private val LightGreen = Color(0xFFDCEBDD)
private val DeleteRed = Color(0xFFB64B46)

@Composable
fun ScanHistoryScreen(
    historyViewModel: ScanHistoryViewModel,
    preferencesViewModel: HistoryPreferencesViewModel,
    productViewModel: ProductViewModel,
    onBack: () -> Unit,
    onProductClick: (Int) -> Unit,
    onScanClick: () -> Unit
) {
    val history by historyViewModel.history.collectAsState()
    val newestFirst by preferencesViewModel.newestFirst.collectAsState()
    val products by productViewModel.products.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showClearDialog by remember { mutableStateOf(false) }
    var editingProductId by remember { mutableStateOf<Int?>(null) }
    var noteText by remember { mutableStateOf("") }

    val filteredHistory = history
        .filter { record ->
            val product = products.find {
                it.id == record.productId
            }

            product != null && (
                    product.name.contains(searchQuery, ignoreCase = true) ||
                            product.brand.contains(searchQuery, ignoreCase = true)
                    )
        }
        .let { scans ->
            if (newestFirst) {
                scans.sortedByDescending { it.savedAt }
            } else {
                scans.sortedBy { it.savedAt }
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
                        "Scan history",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        bottomBar = {
            Button(
                onClick = onScanClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Green),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Scan a product")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search scans") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Green,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(Modifier.height(22.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "History order",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )

                    Text(
                        text = if (newestFirst) {
                            "Newest first"
                        } else {
                            "Oldest first"
                        },
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Switch(
                    checked = newestFirst,
                    onCheckedChange = { value ->
                        preferencesViewModel.setNewestFirst(value)
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Green
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            when {
                history.isEmpty() -> {
                    HistoryEmptyState(onScanClick)
                }

                filteredHistory.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No matching scans found", color = Color.Gray)
                    }
                }

                else -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Recent scans · ${filteredHistory.size}",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                        TextButton(onClick = { showClearDialog = true }) {
                            Text("Clear history", color = DeleteRed)
                        }
                    }
                    Spacer(Modifier.height(12.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 18.dp)
                    ) {
                        items(
                            items = filteredHistory,
                            key = { it.productId }
                        ) { record ->
                            val product = products.find { it.id == record.productId }
                            if (product != null) {
                                HistoryProductCard(
                                    product = product,
                                    record = record,
                                    onClick = { onProductClick(product.id) },
                                    onEdit = {
                                        noteText = record.note
                                        editingProductId = record.productId
                                    },
                                    onDelete = { historyViewModel.removeScan(product.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Clear scan history?") },
            text = { Text("All saved scans will be removed from this device.") },
            confirmButton = {
                TextButton(onClick = {
                    historyViewModel.clearHistory()
                    showClearDialog = false
                }) {
                    Text("Clear", color = DeleteRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    editingProductId?.let { productId ->
        AlertDialog(
            onDismissRequest = { editingProductId = null },
            title = { Text("Edit product note") },
            text = {
                OutlinedTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Your note") },
                    placeholder = { Text("Write something about this product") },
                    maxLines = 4
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    historyViewModel.updateNote(productId, noteText)
                    editingProductId = null
                }) {
                    Text("Save", color = Green)
                }
            },
            dismissButton = {
                TextButton(onClick = { editingProductId = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun HistoryEmptyState(onScanClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = LightGreen)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.History,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = Green
            )
            Spacer(Modifier.height(20.dp))
            Text("No scans yet", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Text(
                "Save a product review to keep it here.",
                color = Color.DarkGray,
                fontSize = 14.sp
            )
            Spacer(Modifier.height(20.dp))
            TextButton(onClick = onScanClick) {
                Text("Open scanner", color = Green)
            }
        }
    }
}

@Composable
private fun HistoryProductCard(
    product: Product,
    record: ScanRecord,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val imageRes = when (product.id) {
        1 -> R.drawable.camomile
        2 -> R.drawable.arencia
        3 -> R.drawable.unove
        4 -> R.drawable.aestura
        else -> R.drawable.camomile
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .size(78.dp)
                    .clickable(onClick = onClick),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onClick)
            ) {
                Text(product.brand.uppercase(), fontSize = 11.sp, color = Color.Gray)
                Spacer(Modifier.height(4.dp))
                Text(product.name, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("${product.ingredients.size} ingredients", color = Green, fontSize = 12.sp)
                Spacer(Modifier.height(5.dp))
                Text(
                    DateFormat.getDateTimeInstance(
                        DateFormat.SHORT,
                        DateFormat.SHORT
                    ).format(Date(record.savedAt)),
                    color = Color.Gray,
                    fontSize = 11.sp
                )
                if (record.note.isNotBlank()) {
                    Spacer(Modifier.height(8.dp))
                    Text(text = record.note, color = Green, fontSize = 12.sp, maxLines = 3)
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit note",
                        tint = Green
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete scan",
                        tint = DeleteRed
                    )
                }
            }
        }
    }
}
