package com.example.anshu.android_226.classactivity6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Note(
    val id: Int = System.currentTimeMillis().toInt(),
    val title: String,
    val content: String
)

class NoteViewModel : ViewModel() {
    private val _notes = MutableStateFlow(
        listOf(
            Note(1, "Study Compose", "Learn MVVM Architecture and State Hoisting"),
            Note(2, "Class Activity 6", "Build simple ToDo List app with ViewModel")
        )
    )
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun addNote(title: String, content: String) {
        if (title.isBlank() && content.isBlank()) return
        _notes.update { it + Note(title = title, content = content) }
    }

    fun updateNote(id: Int, title: String, content: String) {
        _notes.update { list ->
            list.map { if (it.id == id) it.copy(title = title, content = content) else it }
        }
    }

    fun deleteNote(note: Note) {
        _notes.update { list -> list.filter { it.id != note.id } }
    }
}

class ToDoListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ToDoListScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(viewModel: NoteViewModel = viewModel()) {
    val notes by viewModel.notes.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedNote by remember { mutableStateOf<Note?>(null) }

    val myColor = Color(0xFF827717)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Notes",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = myColor)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    selectedNote = null
                    showDialog = true
                },
                containerColor = myColor,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (notes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text="No notes available. Tap + to add!",
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(notes, key = { it.id }) { note ->
                        NoteItem(
                            note = note,
                            myColor = myColor,
                            onEdit = {
                                selectedNote = note
                                showDialog = true
                            },
                            onDelete = { viewModel.deleteNote(note) }
                        )
                    }
                }
            }
        }

        if (showDialog) {
            NoteDialog(
                note = selectedNote,
                myColor = myColor,
                onDismiss = { showDialog = false },
                onSave = { title, content ->
                    if (selectedNote == null) {
                        viewModel.addNote(title, content)
                    } else {
                        viewModel.updateNote(selectedNote!!.id, title, content)
                    }
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    myColor: Color,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = myColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = note.content,
                    fontSize = 16.sp
                )
            }
            IconButton(onClick = onEdit) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit Pencil Icon",
                    tint = myColor
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete Icon",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun NoteDialog(
    note: Note?,
    myColor: Color,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (note == null) "Add Note" else "Edit Note",
                color = myColor,
                fontWeight = FontWeight.Bold
            ) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(title, content) },
                colors = ButtonDefaults.buttonColors(containerColor = myColor)
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Gray)
            }
        }
    )
}

