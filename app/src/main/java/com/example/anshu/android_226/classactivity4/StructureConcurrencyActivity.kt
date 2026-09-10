package com.example.anshu.android_226.classactivity4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

class StructureConcurrencyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConcurrencyScreen()
        }
    }
}

// Data Models
data class Review(
    val author: String,
    val rating: Int,
    val comment: String
)

data class Product(
    val name: String,
    val price: String,
    val img: Int,
    val description: String,
    val reviews: List<Review>
)

@Composable
fun ConcurrencyScreen() {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    val productList = listOf(
        Product(
            name = "Wireless Headphones",
            price = "1999",
            img = R.drawable.wirelessheadphone,
            description = "Experience crystal-clear sound with active noise cancellation and ergonomic long-lasting comfort.",
            reviews = listOf(
                Review("Rahul", 5, "Very Good sound quality!"),
                Review("Anita", 4, "Comfortable and clear bass.")
            )
        ),
        Product(
            name = "Smart Watch",
            price = "2999",
            img = R.drawable.smartwatch,
            description = "Track your fitness, heart rate, and stay connected with notifications on the go with this sleek smartwatch.",
            reviews = listOf(
                Review("Vikas", 5, "Nice display and battery life."),
                Review("Pooja", 4, "Step counter is very accurate.")
            )
        ),
        Product(
            name = "Bluetooth Speaker",
            price = "1499",
            img = R.drawable.bluespeaker,
            description = "Powerful bass, portable design, and waterproof rating make this bluetooth speaker perfect for outdoor parties.",
            reviews = listOf(
                Review("Amit", 5, "Wonderful sound!"),
                Review("Sneha", 5, "Loud and clear outdoors.")
            )
        )
    )

    if (selectedProduct == null) {
        ProductListScreen(products = productList, onProductClick = { selectedProduct = it })
    } else {
        ProductDetailScreen(product = selectedProduct!!, onBackClick = { selectedProduct = null })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(products: List<Product>, onProductClick: (Product) -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Product List", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF9800),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products) { product ->
                ProductItemCard(product = product, onClick = { onProductClick(product) })
            }
        }
    }
}

@Composable
fun ProductItemCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = product.img),
                contentDescription = product.name,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = product.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Price: ₹${product.price}", fontSize = 14.sp, color = Color.DarkGray)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(product: Product, onBackClick: () -> Unit) {
    var isSearching by remember { mutableStateOf(false) }

    if (isSearching) {
        SearchingScreen(onCancel = { isSearching = false })
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Item Detail", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFFF9800),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Product Image Centered
                Image(
                    painter = painterResource(id = product.img),
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Left-aligned Text Information
                Text(text = product.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Price: ₹${product.price}", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFFF9800))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = product.description, fontSize = 14.sp, color = Color.DarkGray)

                Spacer(modifier = Modifier.height(16.dp))

                // Customer Review Section Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Customer Review",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE65100)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        product.reviews.forEach { review ->
                            ReviewRow(review = review)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                // Push Load More button to the bottom
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { isSearching = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))
                ) {
                    Text(text = "Load More Review")
                }
            }
        }
    }
}

@Composable
fun ReviewRow(review: Review) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = review.author, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
            Text(text = review.comment, fontSize = 13.sp, color = Color.Gray)
        }
        Row {
            repeat(review.rating) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchingScreen(onCancel: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Searching", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF9800),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFFFF9800),
                    modifier = Modifier.size(56.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Searching reviews...",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onCancel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))
                ) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}
