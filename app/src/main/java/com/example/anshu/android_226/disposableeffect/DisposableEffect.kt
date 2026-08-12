package com.example.anshu.android_226.disposableeffect

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anshu.android_226.R

@Composable
fun DisposableEffectExample() {
    var showScreen by remember { mutableStateOf(true) }
    val context = LocalContext.current

    if (showScreen) {
        Image(
            painter = painterResource(id = R.drawable.img_1),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Main Screen is Active",
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
            )
            
            Button(
                modifier = Modifier
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                onClick = { showScreen = false }
            ) {
                Text("Click to Dispose and Switch")
            }

            DisposableEffect(Unit) {
                onDispose {
                    Toast.makeText(context, "Screen Disposed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    } else {
        DisposalScreenUI()
    }
}

@Composable
fun DisposalScreenUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_2),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(250.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Disposal Screen",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
        }
    }
}