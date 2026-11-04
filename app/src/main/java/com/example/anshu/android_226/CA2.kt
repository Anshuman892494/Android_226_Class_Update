package com.example.anshu.android_226

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CA2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                FoodOrderScreen()
            }
        }
    }
}

data class FoodItem(
    val id: Int,
    val name: String,
    val price: Int
)

val sampleFoodList = listOf(
    FoodItem(1, "Pizza", 250),
    FoodItem(2, "Burger", 150),
    FoodItem(3, "Pasta", 200),
    FoodItem(4, "Sandwich", 100),
    FoodItem(5, "French Fries", 90)
)

class OrderViewModel : ViewModel() {

    private val _orderStatus = MutableStateFlow("Select an item to buy")
    val orderStatus: StateFlow<String> = _orderStatus.asStateFlow()

    fun buyFood(foodName: String) {
        viewModelScope.launch {
            _orderStatus.value = "Order Pending for $foodName"
            delay(1500)

            _orderStatus.value = "Order Accepted for $foodName"
            delay(1500)

            _orderStatus.value = "Making $foodName"
            delay(1500)

            _orderStatus.value = "$foodName is Out for Delivery"
            delay(1500)

            _orderStatus.value = "$foodName Delivered Successfully!"
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodOrderScreen(viewModel: OrderViewModel = viewModel()) {
    val status by viewModel.orderStatus.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Food Order Tracker", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF9800))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Order Status",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = status,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(sampleFoodList) { food ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${food.name} - ₹${food.price}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Button(
                                onClick = { viewModel.buyFood(food.name) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                            ) {
                                Text("Buy")
                            }
                        }
                    }
                }
            }
        }
    }
}
