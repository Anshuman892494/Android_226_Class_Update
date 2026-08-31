package com.example.anshu.android_226.classactivity3

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.*

class CoroutineDemo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ActivityScreen3()
            }
        }
    }
}

class DemoViewModel : ViewModel() {
    var status by mutableStateOf("Idle")
        private set
    var dispatcherName by mutableStateOf("None")
        private set
    var isDataLoaded by mutableStateOf(false)
        private set

//    fun loadData() {
//        viewModelScope.launch(Dispatchers.IO) {
//            status = "Loading..."
//            dispatcherName = "Dispatchers.IO"
//            delay(2000)
//            isDataLoaded = true
//            status = "Data Loaded"
//        }
//    }

    fun loadData() {
        viewModelScope.launch {
            // 1. Starts on Main by default
            status = "Initializing..." 
            dispatcherName = "Main Thread: ${Thread.currentThread().name}"
            delay(3000)

            // 2. Switch to IO for fetching
            val rawData = withContext(Dispatchers.IO) {
                dispatcherName = "IO Dispatcher: ${Thread.currentThread().name}"
                delay(3000)
                "Data Fetched"
            }

            status = "Processing Data..." 
            dispatcherName = "Main Thread: ${Thread.currentThread().name}"

            // 3. Switch to Default for heavy calculation
            val processedData = withContext(Dispatchers.Default) {
                dispatcherName = "Default Dispatcher: ${Thread.currentThread().name}"
                delay(3000)
                "Result: $rawData"
            }

            // 4. Final UI Update
            status = processedData
            dispatcherName = "Main Thread: ${Thread.currentThread().name}"
            isDataLoaded = true
        }
    }

    fun cancelAllJobs() {
        viewModelScope.coroutineContext.cancelChildren()
        status = "Cancelled"
        dispatcherName = "None"
        isDataLoaded = false
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen3(viewModel: DemoViewModel = viewModel()) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Coroutine Demo",
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
        ) {
            Text(
                text = "Data Loader",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Status: ${viewModel.status}" ,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Dispatcher: ${viewModel.dispatcherName}",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { viewModel.loadData() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF008000)
                    )
                ) {
                    Text("Load Data")
                }
                Button(
                    onClick = { viewModel.cancelAllJobs() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336)
                    )
                ) {
                    Text("Cancel")
                }

                //Adding button for backspace and return to home
                Button(
                    onClick = { (context as? ComponentActivity)?.finish() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF03A9F4)
                    )
                ) {
                    Text("Home")
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Items:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (viewModel.isDataLoaded) {
                    for (i in 1..10) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF5F5F5)
                                )
                            ) {
                                Text(
                                    text = "Item $i",
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
