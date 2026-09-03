package com.example.anshu.android_226.Unit_2.coroutinebacis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class CoroutineBasicsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CoroutineScreen()
                }
            }
        }
    }
}

@Composable
fun CoroutineScreen() {
    var orderStatus by remember { mutableStateOf("Order Not Placed") }
    var isLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(isLoaded) {
        if (isLoaded) {
            orderStatus = "Processing order..."
            delay(3000)
            orderStatus = "Food ordered successfully"
            isLoaded = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = orderStatus,
            fontSize = 32.sp,
            color = if (orderStatus == "Food ordered successfully") Color(0xFF008000) else Color(0xFFFF0000),
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { isLoaded = true },
            enabled = !isLoaded,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffff0000)
            )

        ) {
            Text(
                text = if (orderStatus == "Food ordered successfully") "Order Again" else "Place Order"
            )
        }
    }
}
