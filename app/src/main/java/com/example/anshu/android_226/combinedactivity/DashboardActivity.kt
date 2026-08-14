package com.example.anshu.android_226.combinedactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

class DashboardActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                            title = { Text("App Dashboard", color = Color(0xFFFFFFFF)) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent,
                                titleContentColor = Color(0xFFFFFFFF),
                                actionIconContentColor = Color(0xFFFFFFFF)
                            ),
                            actions = {
                                IconButton(onClick = {
                                    startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
                                    finishAffinity()
                                }) {
                                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout", tint = Color(0xFFFFFFFF))
                                }
                            }
                        )
                    }
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Welcome Back!",
                            style = TextStyle(
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD32F2F)
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Explore the various Compose features below.",
                            style = TextStyle(fontSize = 16.sp, color = Color(0xFFFFFFFF))
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        DashboardButton("DisposableEffect Demo") {
                            startActivity(Intent(this@DashboardActivity, DisposableEffectActivity::class.java))
                        }
                        DashboardButton("LaunchedEffect Demo") {
                            startActivity(Intent(this@DashboardActivity, LaunchedEffectActivity::class.java))
                        }
                        DashboardButton("Modal Bottom Sheet") {
                            startActivity(Intent(this@DashboardActivity, BottomSheetActivity::class.java))
                        }
                        DashboardButton("Alert Dialog") {
                            startActivity(Intent(this@DashboardActivity, DialogActivity::class.java))
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        OutlinedButton(
                            onClick = {
                                startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
                                finishAffinity()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFD32F2F)),
                            border = ButtonDefaults.outlinedButtonBorder(true).copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFD32F2F)))
                        ) {
                            Text("Logout")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun DashboardButton(text: String, onClick: () -> Unit) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
        ) {
            Text(text, color = Color(0xFFFFFFFF))
        }
    }
}
