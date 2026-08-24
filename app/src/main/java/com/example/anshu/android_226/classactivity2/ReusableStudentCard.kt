package com.example.anshu.android_226.classactivity2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Student(
    val name: String,
    val branch: String,
    val emailId: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentCardScreen() {
    val students = listOf(
        Student("Anshu Kumar", "Computer Science", "anshu@example.com"),
        Student("Rahul Sharma", "Information Technology", "rahul@example.com"),
        Student("Priya Singh", "Electronics", "priya@example.com"),
        Student("Amit Patel", "Mechanical", "amit@example.com"),
        Student("Sneha Reddy", "Civil Engineering", "sneha@example.com"),
        Student("Anshuman", "Computer Science", "anshu@example.com"),
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Student List", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFCDDC39),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(students) { student ->
                StudentCard(student = student)
            }
        }
    }
}

@Composable
fun StudentCard(student: Student) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Student Icon",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = student.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Branch: ${student.branch}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Email: ${student.emailId}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
