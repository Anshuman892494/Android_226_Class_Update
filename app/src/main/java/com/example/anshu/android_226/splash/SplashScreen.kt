package com.example.anshu.android_226.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anshu.android_226.R

@Composable
fun SplashScreenUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
       Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "SplashScreen",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
            .padding(bottom = 16.dp)
        )
    }
}