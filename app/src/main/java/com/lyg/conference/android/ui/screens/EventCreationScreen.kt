package com.lyg.conference.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyg.conference.models.ConferenceEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCreationScreen(
    onNavigateBack: () -> Unit,
    onAddEvent: () -> Unit
) {
    var events by remember { mutableStateOf(getSampleEvents()) }
    var showDeleteDialog by remember { mutableStateOf<ConferenceEvent?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Event Creation",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            FloatingActionButton(
                onClick = onAddEvent,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Event")
            }
        }

        // Events List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(events) { event ->
                EventCard(
                    event = event,
                    onEdit = { /* TODO: Navigate to edit event */ },
                    onDelete = { showDeleteDialog = event }
                )
            }
        }
    }

    // Delete Confirmation Dialog
    showDeleteDialog?.let { event ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Delete Event") },
            text = { Text("Are you sure you want to delete '${event.name}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        events = events.filter { it.id != event.id }
                        showDeleteDialog = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventCard(
    event: ConferenceEvent,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Event,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = event.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${event.startDate} - ${event.endDate}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Text(
                            text = event.location,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
                
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
            
            if (event.description.isNotEmpty()) {
                Text(
                    text = event.description,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            
            // Event Status and Capacity
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    color = when (event.status) {
                        "Active" -> MaterialTheme.colorScheme.primaryContainer
                        "Draft" -> MaterialTheme.colorScheme.secondaryContainer
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = event.status,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                
                Text(
                    text = "${event.registeredAttendees}/${event.maxCapacity} attendees",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// Sample data for demonstration
private fun getSampleEvents(): List<ConferenceEvent> {
    return listOf(
        ConferenceEvent(
            id = "1",
            name = "Louisville Youth Group Conference 2024",
            description = "Annual conference bringing together youth from across Louisville for worship, learning, and fellowship.",
            startDate = "2024-07-20",
            endDate = "2024-07-22",
            location = "Louisville Convention Center",
            maxCapacity = 500,
            registeredAttendees = 342,
            status = "Active"
        ),
        ConferenceEvent(
            id = "2",
            name = "Summer Youth Retreat",
            description = "3-day retreat focused on spiritual growth and community building.",
            startDate = "2024-08-15",
            endDate = "2024-08-17",
            location = "Camp Crossroads",
            maxCapacity = 150,
            registeredAttendees = 89,
            status = "Active"
        ),
        ConferenceEvent(
            id = "3",
            name = "Fall Leadership Summit",
            description = "Leadership development conference for youth leaders and mentors.",
            startDate = "2024-10-12",
            endDate = "2024-10-13",
            location = "Faith Community Center",
            maxCapacity = 100,
            registeredAttendees = 0,
            status = "Draft"
        )
    )
}
