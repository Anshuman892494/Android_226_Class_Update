package com.example.anshu.android_226.classactivity5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

data class NewsArticle(
    val title: String,
    val description: String
)

class NewsViewModel : ViewModel() {

    private suspend fun fetchNewsList(): List<NewsArticle> {
        delay(5000)
        return listOf(
            NewsArticle("Understanding CPU Cores", "Central Processing Units feature multiple cores and threads to execute computational tasks and handle multitasking efficiently."),
            NewsArticle("RAM vs Storage (SSD/HDD)", "Random Access Memory provides fast temporary workspace, while Solid State Drives store permanent data with high read/write speeds."),
            NewsArticle("Importance of Power Supply (PSU)", "A reliable power supply ensures stable voltage and current delivery to all internal PC components, protecting hardware from surges."),
            NewsArticle("Operating System Fundamentals", "Operating systems like Windows, macOS, and Linux manage hardware resources, memory allocation, and user interfaces."),
            NewsArticle("GPU & Graphics Rendering", "Graphics Processing Units accelerate image rendering, video editing, and gaming performance by handling parallel computations.")
        )
    }


//    private suspend fun fetchNewsList(): List<NewsArticle> {
//        val url = URL("https://jsonplaceholder.typicode.com/posts")
//        val connection = withContext(Dispatchers.IO) {
//            url.openConnection()
//        } as HttpURLConnection
//
//        try {
//            connection.requestMethod = "GET"
//            connection.connectTimeout = 8000
//            connection.readTimeout = 8000
//
//            val jsonString = connection.inputStream.bufferedReader().use { it.readText() }
//            val jsonArray = JSONArray(jsonString)
//            val list = mutableListOf<NewsArticle>()
//
//            for (i in 0 until minOf(jsonArray.length(), 15)) {
//                val obj = jsonArray.getJSONObject(i)
//                list.add(
//                    NewsArticle(
//                        title = obj.getString("title"),
//                        description = obj.getString("body")
//                    )
//                )
//            }
//            return list
//        } finally {
//            connection.disconnect()
//        }
//    }

    val newsFlow: Flow<List<NewsArticle>> = flow {
        val articles = withContext(Dispatchers.IO) {
            fetchNewsList()
        }
        emit(articles)
    }
    .flowOn(Dispatchers.IO)
    .catch {
        emit(emptyList())
    }
}

class NewsAPIActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                NewsScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(viewModel: NewsViewModel = viewModel()) {
    val newsList by viewModel.newsFlow.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News App", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFC62828),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (newsList.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFFC62828)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(newsList) { article ->
                        NewsCard(article = article)
                    }
                }
            }
        }
    }
}

@Composable
fun NewsCard(article: NewsArticle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = article.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB71C1C)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = article.description,
                fontSize = 14.sp,
                color = Color(0xFFC62828).copy(alpha = 0.85f)
            )
        }
    }
}
