
package com.example.skincareapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import com.example.skincareapp.view.SkinProfileScreen
import com.example.skincareapp.viewmodel.SkinProfileViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.skincareapp.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Green = Color(0xFF238367)
private val Cream = Color(0xFFFAFAF5)
private val LightGreen = Color(0xFFDCEBDD)
private val TextDark = Color(0xFF222222)
private val TextGray = Color(0xFF777777)

@Composable
fun HomeScreen(
    onProductsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onScanClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = Cream,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                val tabs = listOf(
                    Triple("Home", Icons.Default.Home, true),
                    Triple("Products", Icons.Default.ShoppingBag, false),
                    Triple("Scan", Icons.Default.CenterFocusStrong, false),
                    Triple("Explore", Icons.Default.Explore, false),
                    Triple("Profile", Icons.Default.Person, false)
                )

                tabs.forEach { (title, icon, selected) ->
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            when (title) {
                                "Products" -> onProductsClick()
                                "Profile" -> onProfileClick()
                                "Scan" -> onScanClick()
                            }
                        },

                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = title
                            )
                        },
                        label = {
                            Text(
                                text = title,
                                fontSize = 10.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Green,
                            selectedTextColor = Green,
                            indicatorColor = Color(0xFFE8F3EE),
                            unselectedIconColor = TextGray,
                            unselectedTextColor = TextGray
                        )
                    )
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome back",
                fontSize = 14.sp,
                color = TextGray
            )

            Text(
                text = "Hi, Jessica!",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "UV Index",
                            color = TextGray,
                            fontSize = 13.sp
                        )

                        Text(
                            "Almaty",
                            color = TextGray,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        "4 · Moderate",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    Text(
                        "Apply sunscreen with SPF 30 or higher.",
                        fontSize = 12.sp,
                        color = TextGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LightGreen
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        "Your routine starts here",
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        "No steps added yet.",
                        fontSize = 13.sp,
                        color = TextGray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Create a routine",
                            fontWeight = FontWeight.SemiBold,
                            color = Green
                        )

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Green
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                "Explore",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ExploreCard(
                    title = "Scanner",
                    icon = Icons.Default.DocumentScanner,
                    color = LightGreen,
                    modifier = Modifier.weight(1f)
                )

                ExploreCard(
                    title = "Search",
                    icon = Icons.Default.Search,
                    color = Color(0xFFE7DFF2),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ExploreCard(
                    title = "Skincare Finder",
                    icon = Icons.Default.Spa,
                    color = Color(0xFFF5DFE1),
                    modifier = Modifier.weight(1f)
                )

                ExploreCard(
                    title = "Hair Lab",
                    icon = Icons.Default.Face,
                    color = Color(0xFFDCE7F1),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Popular Scans",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Text(
                    "See All ›",
                    color = Green,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(14.dp))


            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProductCard(
                    brand = "ARENCIA",
                    name = "Vitamin C Booster",
                    imageRes = R.drawable.arencia,
                    modifier = Modifier.weight(1f)
                )

                ProductCard(
                    brand = "UNOVE",
                    name = "Deep Damage Repair",
                    imageRes = R.drawable.unove,
                    modifier = Modifier.weight(1f)
                )

                ProductCard(
                    brand = "AESTURA",
                    name = "ATOBARRIER365",
                    imageRes = R.drawable.aestura,
                    modifier = Modifier.weight(1f)
                )
            }


            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ExploreCard(
    title: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.height(145.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(48.dp),
                tint = Green
            )

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }
    }
}

@Composable
fun ProductCard(
    brand: String,
    name: String,
    imageRes: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(175.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(105.dp),
                contentScale = ContentScale.Fit
            )

            Column {
                Text(
                    text = brand,
                    fontSize = 9.sp,
                    color = TextGray
                )

                Text(
                    text = name,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark,
                    lineHeight = 13.sp,
                    maxLines = 2
                )
            }
        }
    }
}
