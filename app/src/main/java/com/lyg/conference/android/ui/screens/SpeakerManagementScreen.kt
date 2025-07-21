package com.lyg.conference.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyg.conference.models.Speaker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakerManagementScreen(
    onNavigateBack: () -> Unit,
    onAddSpeaker: () -> Unit
) {
    var speakers by remember { mutableStateOf(getSampleSpeakers()) }
    var showDeleteDialog by remember { mutableStateOf<Speaker?>(null) }

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
                text = "Speaker Management",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            FloatingActionButton(
                onClick = onAddSpeaker,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Speaker")
            }
        }

        // Speakers List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(speakers) { speaker ->
                SpeakerCard(
                    speaker = speaker,
                    onEdit = { /* TODO: Navigate to edit speaker */ },
                    onDelete = { showDeleteDialog = speaker }
                )
            }
        }
    }

    // Delete Confirmation Dialog
    showDeleteDialog?.let { speaker ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Delete Speaker") },
            text = { Text("Are you sure you want to delete '${speaker.name}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        speakers = speakers.filter { it.id != speaker.id }
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
private fun SpeakerCard(
    speaker: Speaker,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Speaker Avatar
            Surface(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Speaker Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = speaker.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = speaker.title,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp)
                )
                if (speaker.organization.isNotEmpty()) {
                    Text(
                        text = speaker.organization,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                if (speaker.email.isNotEmpty()) {
                    Text(
                        text = speaker.email,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
            
            // Action Buttons
            Column {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
        
        if (speaker.bio.isNotEmpty()) {
            Text(
                text = speaker.bio,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

// Sample data for demonstration
private fun getSampleSpeakers(): List<Speaker> {
    return listOf(
        Speaker(
            id = "1",
            name = "Pastor John Smith",
            title = "Senior Pastor",
            organization = "Louisville Community Church",
            email = "john.smith@lcc.org",
            bio = "Pastor John has been serving in youth ministry for over 15 years and is passionate about developing young Christian leaders."
        ),
        Speaker(
            id = "2",
            name = "Sarah Johnson",
            title = "Youth Director",
            organization = "Faith Baptist Church",
            email = "sarah.johnson@faithbaptist.org",
            bio = "Sarah specializes in leadership development and has led numerous youth conferences across Kentucky."
        ),
        Speaker(
            id = "3",
            name = "David Wilson",
            title = "Worship Leader",
            organization = "Grace Fellowship",
            email = "david.wilson@gracefellowship.org",
            bio = "David is a contemporary Christian music artist and worship leader with a heart for youth ministry."
        ),
        Speaker(
            id = "4",
            name = "Dr. Emily Davis",
            title = "Christian Counselor",
            organization = "Hope Counseling Center",
            email = "emily.davis@hopecounseling.org",
            bio = "Dr. Davis specializes in adolescent psychology and Christian counseling, helping young people navigate life's challenges."
        )
    )
}
