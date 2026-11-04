package com.example.anshu.android_226.Unit_4

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anshu.android_226.Unit_4.db.StudentDao
import com.example.anshu.android_226.Unit_4.db.StudentDatabase
import com.example.anshu.android_226.Unit_4.db.StudentEntity

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// VIEWMODEL LAYER

class StudentViewModel(private val dao: StudentDao) : ViewModel() {

    // Reactive UI state stream driven directly by Room DB changes
    val studentList: StateFlow<List<StudentEntity>> = dao.getAllStudents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addStudent(name: String, rollNumber: String, courseCode: String) {
        if (name.isBlank() || rollNumber.isBlank()) return

        viewModelScope.launch {
            dao.insertStudent(
                StudentEntity(
                    name = name.trim(),
                    rollNumber = rollNumber.trim(),
                    courseCode = courseCode.trim().ifEmpty { "CSE226" }
                )
            )
        }
    }

    fun deleteStudent(student: StudentEntity) {
        viewModelScope.launch {
            dao.deleteStudent(student)
        }
    }
}

/**
 * Factory class to instantiate ViewModel with DAO dependency without Hilt/Koin.
 */
class StudentViewModelFactory(private val dao: StudentDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// JETPACK COMPOSE UI LAYER

class RoomDatabaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = StudentDatabase.getDatabase(applicationContext)
        val factory = StudentViewModelFactory(database.studentDao())

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val viewModel: StudentViewModel = viewModel(factory = factory)
                    StudentScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun StudentScreen(viewModel: StudentViewModel) {
    val students by viewModel.studentList.collectAsStateWithLifecycle()

    var nameInput by remember { mutableStateOf("") }
    var rollInput by remember { mutableStateOf("") }
    var courseInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Room DB: Student Directory",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input Form
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Add New Student Record", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Student Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = rollInput,
                    onValueChange = { rollInput = it },
                    label = { Text("Roll Number") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = courseInput,
                    onValueChange = { courseInput = it },
                    label = { Text("Course Code (e.g., CSE226)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        viewModel.addStudent(nameInput, rollInput, courseInput)
                        nameInput = ""
                        rollInput = ""
                        courseInput = ""
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Save to Room DB")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Stored Records (${students.size})",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        // List Displaying Database Entries
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(students, key = { it.id }) { student ->
                StudentRowItem(
                    student = student,
                    onDelete = { viewModel.deleteStudent(student) }
                )
            }
        }
    }
}

@Composable
fun StudentRowItem(
    student: StudentEntity,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = student.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Roll: ${student.rollNumber} | Course: ${student.courseCode}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Record",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
