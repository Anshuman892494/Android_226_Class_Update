package com.example.anshu.android_226

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.anshu.android_226.Unit_1.composeanimationapi.AnimationStateScreen
import com.example.anshu.android_226.Unit_1.customcomposables.SimpleCardScreen
import com.example.anshu.android_226.classactivity2.ProductListScreen
import com.example.anshu.android_226.classactivity2.StudentCardScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {

//                AnimationStateScreen()
                SimpleCardScreen()

            }
        }
    }