package com.lyg.conference.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyg.conference.models.Session
import kotlinx.datetime.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleManagementScreen(
    onNavigateBack: () -> Unit,
    onAddSession: () -> Unit
) {
    var sessions by remember { mutableStateOf(getSampleSessions()) }
    var showDeleteDialog by remember { mutableStateOf<Session?>(null) }

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
                text = "Schedule Management",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            FloatingActionButton(
                onClick = onAddSession,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Session")
            }
        }

        // Sessions List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sessions) { session ->
                SessionCard(
                    session = session,
                    onEdit = { /* TODO: Navigate to edit session */ },
                    onDelete = { showDeleteDialog = session }
                )
            }
        }
    }

    // Delete Confirmation Dialog
    showDeleteDialog?.let { session ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Delete Session") },
            text = { Text("Are you sure you want to delete '${session.title}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        sessions = sessions.filter { it.id != session.id }
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
private fun SessionCard(
    session: Session,
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = session.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (session.speakerIds.isNotEmpty()) "Speaker: ${session.speakerIds.first()}" else "No speaker assigned",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "${session.startTime.toString().take(16)} - ${session.endTime.toString().take(16)}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Text(
                        text = session.location,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
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
            
            if (session.description.isNotEmpty()) {
                Text(
                    text = session.description,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

// Sample data for demonstration
private fun getSampleSessions(): List<Session> {
    return listOf(
        Session(
            id = "1",
            title = "Opening Ceremony",
            description = "Welcome to the Louisville Youth Group Conference 2024",
            speakerIds = listOf("speaker-1"),
            startTime = Instant.parse("2024-07-20T09:00:00.000Z"),
            endTime = Instant.parse("2024-07-20T10:00:00.000Z"),
            location = "Main Auditorium"
        ),
        Session(
            id = "2",
            title = "Youth Leadership Workshop",
            description = "Learn essential leadership skills for young Christians",
            speakerIds = listOf("speaker-2"),
            startTime = Instant.parse("2024-07-20T10:30:00.000Z"),
            endTime = Instant.parse("2024-07-20T12:00:00.000Z"),
            location = "Conference Room A"
        ),
        Session(
            id = "3",
            title = "Music & Worship",
            description = "Interactive worship session with contemporary Christian music",
            speakerIds = listOf("speaker-3"),
            startTime = Instant.parse("2024-07-20T14:00:00.000Z"),
            endTime = Instant.parse("2024-07-20T15:30:00.000Z"),
            location = "Chapel"
        )
    )
}
