package com.example.anshu.android_226.coroutinebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentResultCalculate : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                StudentResultScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentResultScreen() {
    val scope = rememberCoroutineScope()
    var status by remember { mutableStateOf("Welcome to Student Portal") }
    var resultText by remember { mutableStateOf("") }

    suspend fun connect() {
        withContext(Dispatchers.IO) {
            delay(2000)
        }
    }

    suspend fun calculate(): Int {
        return withContext(Dispatchers.Default) {
            delay(1500)
            85 + 90 + 78
        }
    }

    suspend fun checkStatus(): String {
        delay(1000)
        return "Student Result: PASSED (Grade A)"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Student Result App",
                        fontWeight = FontWeight.Bold
                    )},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF673AB7),
                    titleContentColor = Color(0xFFFFFFFF)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = status,
                style = MaterialTheme.typography.titleLarge
            )

            if (resultText.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = resultText,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    scope.launch {
                        status = "Connecting to Server..."
                        connect()
                        status = "Connected"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Connect to Server")
            }

            Button(
                onClick = {
                    scope.launch {
                        status = "Calculating..."
                        val total = calculate()
                        resultText = "Total Marks: $total"
                        status = "Done"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calculate Total")
            }

            Button(
                onClick = {
                    scope.launch {
                        status = "Checking Status..."
                        resultText = checkStatus()
                        status = "Result Ready"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Check Result")
            }
        }
    }
}
