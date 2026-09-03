package com.example.anshu.android_226.Unit_1.composeanimationapi

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimationStateScreen() {
    var isExpanded by remember { mutableStateOf(false) }

    val backgroundColor by animateColorAsState(
        targetValue = if (!isExpanded) Color(0xFF6200EE) else Color(0xFF03DAC5),
        animationSpec = tween(durationMillis = 2500),
        label = "ColorAnimation"
    )

    val boxSize by animateDpAsState(
        targetValue = if (isExpanded) 250.dp else 120.dp,
        animationSpec = tween(durationMillis = 1500),
        label = "SizeAnimation"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(boxSize)
                .clip(RoundedCornerShape(if (isExpanded) 24.dp else 8.dp))
                .background(backgroundColor),
//                .clickable { isExpanded = !isExpanded },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isExpanded) "Big Box" else "Small Box",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { isExpanded = !isExpanded }) {
            Text(text = "Click Here")
        }
    }
}

