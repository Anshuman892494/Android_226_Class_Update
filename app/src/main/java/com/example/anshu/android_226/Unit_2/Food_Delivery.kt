package com.example.anshu.android_226.Unit_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*

class Food_Delivery : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                FoodDeliveryScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDeliveryScreen() {
    val scope = rememberCoroutineScope()

    var orderState by remember { mutableStateOf("Not Started") }
    var paymentState by remember { mutableStateOf("Not Started") }
    var stockState by remember { mutableStateOf("Not Started") }
    var mainStatus by remember { mutableStateOf("Tap Place Order to begin") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Food Delivery App", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFCDDC39),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = mainStatus,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5722)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Order Status: $orderState",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Payment Status: $paymentState",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Stock Status: $stockState",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        val orderJob = launch {
                            mainStatus = "Placing Order..."
                            orderState = "Processing..."
                            withContext(Dispatchers.IO) {
                                delay(3000)
                            }
                        }
                        orderJob.join()
                        orderState = "Order Placed"

                        val paymentJob = launch {
                            mainStatus = "Processing Payment..."
                            paymentState = "Processing..."
                            withContext(Dispatchers.IO) {
                                delay(2000)
                            }
                        }
                        paymentJob.join()
                        paymentState = "Payment Successful"

                        val stockJob = launch {
                            mainStatus = "Verifying Stock..."
                            stockState = "Checking Stock..."
                            withContext(Dispatchers.IO) {
                                delay(4000)
                            }
                        }
                        stockJob.join()
                        stockState = "Stock Confirmed"

                        mainStatus = "Order Delivered Successfully!"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCDDC39))
            ) {
                Text("Place Order", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
