package com.example.anshu.android_226.Unit_2.Flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class couroutineflowcollection : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RandomNumberFilterScreen()
            }
        }
    }
}

@Composable
fun RandomNumberFilterScreen() {
    var receivedNumber by remember { mutableStateOf("Waiting...") }

    val marksFlow = flow {
        delay(1000)
        emit(20)
        delay(1000)
        emit(30)
        delay(1000)
        emit(40)
        delay(1000)
        emit(5)
        delay(1000)
        emit(10)
        delay(1000)
        emit(15)
        delay(1000)
        emit(85)
        delay(1000)
        emit(21)
        delay(1000)
        emit(43)
        delay(1000)
        emit(19)
    }

    LaunchedEffect(Unit) {
        marksFlow
            .filter { it < 50 }
            .collect { value ->
                receivedNumber = "Value: $value"
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Marks Flow ",
            fontSize = 32.sp,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = receivedNumber,
            fontSize = 22.sp
        )
    }
}

