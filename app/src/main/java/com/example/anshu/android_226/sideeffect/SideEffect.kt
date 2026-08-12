package com.example.anshu.android_226.sideeffect

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anshu.android_226.R

@Composable
fun SideEffectExample(){
    val context = LocalContext.current
    val counter = remember { mutableStateOf(0) }

    SideEffect {
        Log.d("SideEffect", "Counter is now ${counter.value}")
    }
    Image(
        painter = painterResource(id = R.drawable.img_2),
        contentDescription = "App Logo",
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Counter Value",
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 36.sp,
            modifier = Modifier.padding(24.dp)
        )
        Box(
            modifier = Modifier
                .size(180.dp)
                .shadow(elevation = 16.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(Color.Red.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "${counter.value}",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 80.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { counter.value++ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text("Increment Counter")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (counter.value > 0) {
                    counter.value--
                } else {
                    Toast.makeText(context, "Counter cannot be less than 0", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text("Decrement Counter")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                counter.value = 0
                Toast.makeText(context, "Counter Reset", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text("Reset Counter")

        }
    }
}