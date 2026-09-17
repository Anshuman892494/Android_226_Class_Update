package com.example.anshu.android_226.Unit_2.MVVM

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class mvvmpractice : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                FoodItemScreen()
            }
        }
    }
}

data class FoodItemData(
    val name: String,
    val price: Int,
    var quantity: Int
)

class FoodCartViewModel : ViewModel() {
    var foodItems = mutableStateListOf(
        FoodItemData("Pizza", 100, 3),
        FoodItemData("Burger", 50, 2),
        FoodItemData("Pasta", 80, 1)
    )
        private set

    fun increaseQuantity(foodItem: FoodItemData) {
        val index = foodItems.indexOf(foodItem)
        if (index != -1) {
            foodItems[index] = foodItem.copy(quantity = foodItem.quantity + 1)
        }
    }

    fun decreaseQuantity(foodItem: FoodItemData) {
        val index = foodItems.indexOf(foodItem)
        if (index != -1 && foodItem.quantity > 0) {
            foodItems[index] = foodItem.copy(quantity = foodItem.quantity - 1)
        }
    }

    fun calculateTotal(): Int {
        return foodItems.sumOf { it.price * it.quantity }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemScreen(viewModel: FoodCartViewModel = viewModel()) {
    val items = viewModel.foodItems
    val totalBill = viewModel.calculateTotal()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Food Cart", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 8.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Bill:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "₹$totalBill",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { item ->
                FoodItemCard(
                    item = item,
                    onIncrease = { viewModel.increaseQuantity(item) },
                    onDecrease = { viewModel.decreaseQuantity(item) }
                )
            }
        }
    }
}

@Composable
fun FoodItemCard(
    item: FoodItemData,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Price: ₹${item.price}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onDecrease,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(36.dp)
                ) {
                    Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = "${item.quantity}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                OutlinedButton(
                    onClick = onIncrease,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(36.dp)
                ) {
                    Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
