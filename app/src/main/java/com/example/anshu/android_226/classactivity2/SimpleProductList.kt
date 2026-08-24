package com.example.anshu.android_226.classactivity2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anshu.android_226.R

data class Product(
    val name: String,
    val price: String,
    val img: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen() {
    val products = listOf(
        Product("Wireless Headphones", "1999", R.drawable.wirelessheadphone),
        Product("Smart Watch", "2999", R.drawable.smartwatch),
        Product("Bluetooth Speaker", "1499", R.drawable.bluespeaker),
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Product List", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF008EFF),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(products) { product ->
                ReusableProductCard(
                    name = product.name,
                    price = product.price,
                    imageRes = product.img
                )
            }
        }
    }
}

@Composable
fun ReusableProductCard(
    name: String,
    price: String,
    imageRes: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Price: $price",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            // Add to heart button
            IconButton(
                onClick = { /* Handle heart button click */ },
                modifier = Modifier.size(24.dp)

            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Add to Favorites",
                    tint = Color.Red
                )
            }
        }
    }
}
