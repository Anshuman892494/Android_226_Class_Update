package com.example.anshu.android_226.classactivity1

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.anshu.android_226.R
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen() {
    var currentTime by remember { mutableStateOf("00:00:00") }
    var amPm by remember { mutableStateOf("") }
    var currentDayDate by remember { mutableStateOf("") }
    var isRunning by remember { mutableStateOf(false) }

    fun updateDateTime() {
        val calendar = Calendar.getInstance()
        
        // 12-hour format time
        val timeFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        currentTime = timeFormat.format(calendar.time)
        
        // AM/PM
        val amPmFormat = SimpleDateFormat("a", Locale.getDefault())
        amPm = amPmFormat.format(calendar.time)
        
        // Day and Date
        val dayDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        currentDayDate = dayDateFormat.format(calendar.time)
    }

    LaunchedEffect(Unit) {
        updateDateTime()
    }

    SideEffect {
        Log.d("TimerEffect", "Current time: $currentTime $amPm, Date: $currentDayDate")
    }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            Log.d("TimerEffect", "LaunchedEffect: started")
            while (isRunning) {
                updateDateTime()
                delay(1000L)
            }
        } else {
            Log.d("TimerEffect", "LaunchedEffect: stopped")
        }
    }

    DisposableEffect(isRunning) {
        if (isRunning) {
            Log.d("TimerEffect", "DisposableEffect: Running")
        } else {
            Log.d("TimerEffect", "DisposableEffect: Stopped")
        }

        onDispose {
            Log.d("TimerEffect", "DisposableEffect: Cleanup")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Compose Effect Demo",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD32F2F),
                    titleContentColor = Color(0xFFFFFFFF),
                )
            )
        }
    ) { paddingValues ->
        Image(
            painter = painterResource(id = R.drawable.img_3),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 12.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.9f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 32.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Current Time",
                        color = Color(0xFFD32F2F),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = currentTime,
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    
                    Text(
                        text = amPm,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD32F2F),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = currentDayDate,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center
                    )

                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { isRunning = true },
                    modifier = Modifier
                        .height(56.dp)
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3A833D),
                        contentColor = Color(0xFFFFFFFF)
                    ),
                    enabled = !isRunning,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text(
                        text ="Start Timer",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    onClick = { isRunning = false },
                    modifier = Modifier
                        .height(56.dp)
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F),
                        contentColor = Color(0xFFFFFFFF)
                    ),
                    enabled = isRunning,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text(
                        text ="Stop Timer",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 24.dp),
                text = "Timer Status: ${if (isRunning) "Running" else "Stopped"}",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = if (isRunning) Color(0xFF1B5E20) else Color(0xFFB71C1C),
                textAlign = TextAlign.Center
            )
        }
    }
}