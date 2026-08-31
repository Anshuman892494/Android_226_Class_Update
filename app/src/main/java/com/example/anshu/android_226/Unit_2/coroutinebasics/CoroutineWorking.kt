package com.example.anshu.android_226.Unit_2.coroutinebasics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentResult1Screen() {
    val scope = rememberCoroutineScope()

    var status by remember { mutableStateOf("Welcome to Student Portal") }
    var resultText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student result app") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
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
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
        ) {
            Text(text = status, style = MaterialTheme.typography.titleMedium)
            
            if (resultText.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        text = resultText,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        status = "Connecting to Server..."
                        connectToServer()
                        status = "Connected to Server"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Connect to Server")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        status = "Calculating Subjects Total..."
                        val total = performCalculation()
                        resultText = "Total Marks in Subjects: $total"
                        status = "Calculation Completed"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calculate Result")
            }

            Button(
                onClick = {
                    scope.launch {
                        status = "Checking Final Status..."
                        val finalStatus = checkResult()
                        resultText = finalStatus
                        status = "Result Ready"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Result Check")
            }
        }
    }
}

suspend fun connectToServer() {
    withContext(Dispatchers.IO) {
        delay(2000) // Simulating network lag
        println("Server connected on: ${Thread.currentThread().name}")
    }
}

suspend fun performCalculation(): Int {
    return withContext(Dispatchers.Default) {
        delay(1500)
        val maths = 85
        val science = 90
        val english = 78
        val total = maths + science + english
        println("Calculation done on: ${Thread.currentThread().name}")
        total
    }
}
suspend fun checkResult(): String {
    return withContext(Dispatchers.Main) {
        delay(1000) // Small delay
        println("Result checked on: ${Thread.currentThread().name}")
        "Student Result: PASSED (Grade A)\nSubjects: Maths, Science, English"
    }
}
