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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// 1. Simple ViewModel containing Flow
class SimpleDownloadViewModel : ViewModel() {
    
    // StateFlow for Progress (0 to 100)
    private val _progress = MutableStateFlow(0)
    val progress: StateFlow<Int> = _progress.asStateFlow()

    // StateFlow for Status text
    private val _status = MutableStateFlow("Tap the button to start download")
    val status: StateFlow<String> = _status.asStateFlow()

    // Function to simulate file download using Flow
    fun startDownload() {
        viewModelScope.launch {
            _status.value = "Downloading..."
            
            // Creating a simple Flow that emits progress values
            flow {
                for (i in 20..100 step 20) {
                    delay(400L) // Simulate network delay
                    emit(i)     // Emit progress
                }
            }.collect { value ->
                _progress.value = value // Collect emitted values and update state
            }

            _status.value = "Download Completed!"
        }
    }
}

class couroutineflow : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleDownloadScreen()
        }
    }
}

@Composable
fun SimpleDownloadScreen(viewModel: SimpleDownloadViewModel = viewModel()) {
    // Collecting StateFlows as Compose State
    val progress by viewModel.progress.collectAsState()
    val status by viewModel.status.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Simple Flow Downloader",
                fontSize = 22.sp,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = status,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Linear Progress Indicator
            LinearProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$progress% completed",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Download Button
            Button(
                onClick = { viewModel.startDownload() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Start Download")
            }
        }
    }
}
