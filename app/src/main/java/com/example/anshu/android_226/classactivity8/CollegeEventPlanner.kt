package com.example.anshu.android_226.classactivity8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CollegeEvent(
    val eventId: String,
    val title: String,
    val date: String
)

data class Speaker(
    val speakerId: String,
    val name: String,
    val expertise: String,
    val events: MutableList<CollegeEvent> = mutableListOf()
)

class CollegeEventPlannerViewModel : ViewModel() {
    private val _speakers = MutableStateFlow<List<Speaker>>(emptyList())
    val speakers: StateFlow<List<Speaker>> = _speakers.asStateFlow()

    fun addEventToSpeaker(speakerId: String, eventTitle: String, eventDate: String) {
        if (eventTitle.isBlank()) return
        _speakers.update { list ->
            list.map { speaker ->
                if (speaker.speakerId == speakerId) {
                    val newEvents = speaker.events.toMutableList()
                    newEvents.add(
                        CollegeEvent(
                            eventId = "E${System.currentTimeMillis()}",
                            title = eventTitle,
                            date = eventDate.ifBlank { "TBD" }
                        )
                    )
                    speaker.copy(events = newEvents)
                } else {
                    speaker
                }
            }
        }
    }

    fun addSpeaker(name: String, expertise: String) {
        if (name.isBlank()) return
        _speakers.update { list ->
            list + Speaker(
                speakerId = "S${System.currentTimeMillis()}",
                name = name,
                expertise = expertise.ifBlank { "General" },
                events = mutableListOf()
            )
        }
    }
}

class CollegeEventPlannerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CollegeEventPlannerScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollegeEventPlannerScreen(viewModel: CollegeEventPlannerViewModel = viewModel()) {
    val speakers by viewModel.speakers.collectAsState()

    var showAddEventDialog by remember { mutableStateOf(false) }
    var selectedSpeakerId by remember { mutableStateOf("") }
    var eventTitle by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }

    var showAddSpeakerDialog by remember { mutableStateOf(false) }
    var speakerName by remember { mutableStateOf("") }
    var speakerExpertise by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "College Event Planner",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A237E),
                    titleContentColor = Color(0xFFFFFFFF)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddSpeakerDialog = true },
                shape = CircleShape,
                containerColor = Color(0xFF1A237E),
                contentColor = Color(0xFFFFFFFF)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Speaker"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (speakers.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No speakers added yet. Click '+' to add.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            } else {
                items(speakers) { speaker ->
                    SpeakerCard(
                        speaker = speaker,
                        onAddEventClick = {
                            selectedSpeakerId = speaker.speakerId
                            showAddEventDialog = true
                        }
                    )
                }
            }
        }

        // Add Event Dialog
        if (showAddEventDialog) {
            AlertDialog(
                onDismissRequest = { showAddEventDialog = false },
                title = { Text("Add Event") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = eventTitle,
                            onValueChange = { eventTitle = it },
                            label = { Text("Event Title") },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = eventDate,
                            onValueChange = { eventDate = it },
                            label = { Text("Date (e.g. 2025-03-20)") },
                            singleLine = true
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.addEventToSpeaker(selectedSpeakerId, eventTitle, eventDate)
                            eventTitle = ""
                            eventDate = ""
                            showAddEventDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E))
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddEventDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Add Speaker Dialog
        if (showAddSpeakerDialog) {
            AlertDialog(
                onDismissRequest = { showAddSpeakerDialog = false },
                title = { Text("Add Speaker") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = speakerName,
                            onValueChange = { speakerName = it },
                            label = { Text("Speaker Name") },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = speakerExpertise,
                            onValueChange = { speakerExpertise = it },
                            label = { Text("Expertise") },
                            singleLine = true
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.addSpeaker(speakerName, speakerExpertise)
                            speakerName = ""
                            speakerExpertise = ""
                            showAddSpeakerDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E))
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddSpeakerDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun SpeakerCard(speaker: Speaker, onAddEventClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Modifier.let { Arrangement.SpaceBetween },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {

                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = speaker.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = speaker.expertise,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = onAddEventClick) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Event",
                        tint = Color(0xFF1A237E)
                    )
                }
            }

            if (speaker.events.isNotEmpty()) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                speaker.events.forEach { event ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = Color(0xFF3F51B5)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${event.title} (${event.date})",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
