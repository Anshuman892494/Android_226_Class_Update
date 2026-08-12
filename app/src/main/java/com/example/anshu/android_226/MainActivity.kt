package com.example.anshu.android_226

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.anshu.android_226.ui.theme.Android_226Theme
import com.example.anshu.android_226.sideeffect.SideEffectExample
import com.example.anshu.android_226.disposableeffect.DisposableEffectExample
import com.example.anshu.android_226.main.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_226Theme {
//                DisposableEffectExample()
                SideEffectExample()
            }
        }
    }
}
