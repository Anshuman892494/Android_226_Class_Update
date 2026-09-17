package com.example.anshu.android_226.Unit_2.Flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.*

class autosuggesttext : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AutoSuggestScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoSuggestScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<String>>(emptyList()) }

    val allWords = listOf(
        "Apple", "Application", "Applesauce", "Apricot",
        "Banana", "Bandana", "Mango", "Mangoes",
        "Pineapple", "Papaya", "Peach", "Pear"
    )

    LaunchedEffect(searchQuery) {
        flow {
            emit(searchQuery)
        }
            .map { query ->
                if (query.isBlank()) {
                    emptyList()
                } else {
                    allWords.filter { it.contains(query, ignoreCase = true) }
                }
            }
            .collect { result ->
                suggestions = result
            }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Auto Suggestion",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Type here") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (suggestions.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 200.dp)
                            .padding(8.dp)
                    ) {
                        items(suggestions) { suggestion ->
                            ListItem(
                                headlineContent = { Text(suggestion) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        searchQuery = suggestion
                                        suggestions = emptyList()
                                    }
                            )
                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                        }
                    }
                }
            }
        }
    }
}
