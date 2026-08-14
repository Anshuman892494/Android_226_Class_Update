package com.example.anshu.android_226.combinedactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anshu.android_226.R
import kotlinx.coroutines.delay

class LaunchedEffectActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var timer by remember { mutableIntStateOf(0) }

            LaunchedEffect(Unit) {
                while (true) {
                    delay(1000)
                    timer++
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.img_2),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x4D000000))
                )
                Scaffold(
                    containerColor = Color.Transparent,
                    topBar = {
                        TopAppBar(
                            title = { Text("LaunchedEffect", color = Color(0xFFFFFFFF)) },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                        )
                    }
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Timer Running",
                            style = TextStyle(fontSize = 20.sp, color = Color(0xFFFFFFFF), fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "$timer",
                            style = TextStyle(fontSize = 48.sp, color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "seconds elapsed",
                            style = TextStyle(fontSize = 16.sp, color = Color(0xFFFFFFFF))
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = { finish() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                        ) {
                            Text("Back to Dashboard", color = Color(0xFFFFFFFF))
                        }
                    }
                }
            }
        }
    }
}
