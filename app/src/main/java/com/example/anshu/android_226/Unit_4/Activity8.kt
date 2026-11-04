package com.example.anshu.android_226.Unit_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anshu.android_226.R
import com.example.anshu.android_226.ui.theme.Android_226Theme

class Activity8 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomDBTheme {
                RoomDBScreen(name = "Welcome to Room Database")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDBScreen(
    name: String,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text="Room Database Demo",
                        fontWeight = FontWeight.Bold
                    )},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A237E),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = "$name!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1),
                modifier = modifier
            )
            Text(
                text = "This is basic Android application",
                fontSize = 20.sp,
            )

            Spacer(modifier = Modifier.height(20.dp))

//            database icon
//            Icon(
//                painter = painterResource(R.drawable.database),
//                tint = Color(0xFF0D47A1),
//                contentDescription = "contentDescription",
//                modifier = Modifier.width(150.dp)
//            )
            IconButton(
                onClick = {  },
                modifier = Modifier.width(150.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.database),
                    tint = Color(0xFF0D47A1),
                    contentDescription = "contentDescription",
                )
            }
        }
    }
}

@Composable
fun RoomDBTheme(content: @Composable () -> Unit) {
    Android_226Theme(content = content)
}
