package com.example.anshu.android_226.Unit_3.MVVM

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

sealed interface LocationTrackerUiState {
    data object Inactive : LocationTrackerUiState
    data class Active(
        val latitude: Double,
        val longitude: Double,
        val updateCount: Int,
        val observerStatus: String
    ) : LocationTrackerUiState
}

// Lifecycle aware sensor observer
class LocationSensorEngine(private val onStatusChanged: (String) -> Unit) : DefaultLifecycleObserver {
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        onStatusChanged("GPS Hardware Engine: Connected & Streaming")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        onStatusChanged("GPS Hardware Engine: Disconnected")
    }
}

// ViewModel Layer
class LocationTrackerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<LocationTrackerUiState>(LocationTrackerUiState.Inactive)
    val uiState: StateFlow<LocationTrackerUiState> = _uiState.asStateFlow()

    private var trackingJob: Job? = null
    private var updateCounter = 0
    private var currentEngineStatus = "Initialized"

    val sensorEngine = LocationSensorEngine { status ->
        currentEngineStatus = status
        updateStateInCurrentState()
    }

    fun startTracking() {
        if (trackingJob?.isActive == true) return
        trackingJob = viewModelScope.launch {
            var lat = 32.7555
            var lng = 74.8546

            while (isActive) {
                updateCounter++
                lat += 0.0001
                lng += 0.0001
                _uiState.value = LocationTrackerUiState.Active(
                    latitude = lat,
                    longitude = lng,
                    updateCount = updateCounter,
                    observerStatus = currentEngineStatus
                )
                delay(1000L)
            }
        }
    }

    fun stopTracking() {
        trackingJob?.cancel()
        trackingJob = null
        _uiState.value = LocationTrackerUiState.Inactive
    }

    private fun updateStateInCurrentState() {
        val currentState = _uiState.value
        if (currentState is LocationTrackerUiState.Active) {
            _uiState.value = currentState.copy(observerStatus = currentEngineStatus)
        }
    }
}

class LocationTrackingMVVM : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrackingScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingScreen(viewModel: LocationTrackerViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = viewModel.sensorEngine
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Location tracking",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF000080)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (val state = uiState) {
                is LocationTrackerUiState.Inactive -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F8))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Location Tracking Inactive",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF000080)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Press the button below to start tracking location.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Button(
                        onClick = { viewModel.startTracking() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000080))
                    ) {
                        Text(text = "Start Tracking", color = Color.White)
                    }
                }

                is LocationTrackerUiState.Active -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F8))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Live Location Details",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF000080)
                            )
                            HorizontalDivider()
                            Text(text = "Latitude: ${"%.6f".format(state.latitude)}")
                            Text(text = "Longitude: ${"%.6f".format(state.longitude)}")
                            Text(text = "Updates Count: ${state.updateCount}")
                            Text(
                                text = "Status: ${state.observerStatus}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }

                    Button(
                        onClick = { viewModel.stopTracking() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000080))
                    ) {
                        Text(text = "Stop Tracking", color = Color.White)
                    }
                }
            }
        }
    }
}
