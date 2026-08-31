package com.example.anshu.android_226.Unit_1.customcomposables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleCardScreen() {
    val products = listOf(
        "Smartphone", "Laptop", "Wireless Headphones", "Smartwatch", 
        "Bluetooth Speaker", "Gaming Mouse", "Mechanical Keyboard", 
        "Monitor", "Power Bank", "USB-C Cable"
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Product", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* Submit logic */ },
                containerColor = Color.Red,
                contentColor = Color.White,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Submit", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            itemsIndexed(products) { index, productName ->
                ProductCard(name = productName, index = index + 1)
            }
        }
    }
}

@Composable
fun ProductCard(name: String, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Best quality product available at item position $index. Grab it now!",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* Add to cart logic */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Add to Cart", fontSize = 12.sp, color = Color.White)
                }
                
                Button(
                    onClick = { /* Buy now logic */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Buy Now", fontSize = 12.sp, color = Color.White)
                }
            }
        }
    }
}
