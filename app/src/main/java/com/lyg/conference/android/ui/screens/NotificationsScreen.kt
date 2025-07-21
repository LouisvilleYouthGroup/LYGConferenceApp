package com.lyg.conference.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onNavigateBack: () -> Unit,
    onCreateNotification: () -> Unit
) {
    var notifications by remember { mutableStateOf(getSampleNotifications()) }
    var showDeleteDialog by remember { mutableStateOf<NotificationItem?>(null) }

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
                text = "Notifications",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            FloatingActionButton(
                onClick = onCreateNotification,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Notification")
            }
        }

        // Quick Send Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Quick Send",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { /* TODO: Send welcome message */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Welcome")
                    }
                    Button(
                        onClick = { /* TODO: Send schedule update */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Schedule Update")
                    }
                    Button(
                        onClick = { /* TODO: Send reminder */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Reminder")
                    }
                }
            }
        }

        // Notifications List
        Text(
            text = "Notification History",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notifications) { notification ->
                NotificationCard(
                    notification = notification,
                    onDelete = { showDeleteDialog = notification }
                )
            }
        }
    }

    // Delete Confirmation Dialog
    showDeleteDialog?.let { notification ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Delete Notification") },
            text = { Text("Are you sure you want to delete this notification?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        notifications = notifications.filter { it.id != notification.id }
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
private fun NotificationCard(
    notification: NotificationItem,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                        text = notification.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = notification.message,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            when (notification.status) {
                                "Sent" -> Icons.Default.CheckCircle
                                "Scheduled" -> Icons.Default.Schedule
                                else -> Icons.Default.Send
                            },
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = when (notification.status) {
                                "Sent" -> MaterialTheme.colorScheme.primary
                                "Scheduled" -> MaterialTheme.colorScheme.secondary
                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = notification.status,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = notification.sentTime,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
            
            // Recipients and delivery stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Recipients: ${notification.recipients}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (notification.status == "Sent") {
                    Text(
                        text = "Delivered: ${notification.delivered}/${notification.recipients}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

// Data class for notifications
data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val status: String, // "Sent", "Scheduled", "Draft"
    val sentTime: String,
    val recipients: Int,
    val delivered: Int = 0
)

// Sample data
private fun getSampleNotifications(): List<NotificationItem> {
    return listOf(
        NotificationItem(
            id = "1",
            title = "Welcome to LYG Conference 2024!",
            message = "Get ready for an amazing weekend of worship, learning, and fellowship. Check your schedule and don't miss the opening ceremony at 9 AM!",
            status = "Sent",
            sentTime = "2 hours ago",
            recipients = 342,
            delivered = 338
        ),
        NotificationItem(
            id = "2",
            title = "Schedule Update",
            message = "The Youth Leadership Workshop has been moved to Conference Room B. Please check your updated schedule.",
            status = "Sent",
            sentTime = "1 hour ago",
            recipients = 298,
            delivered = 295
        ),
        NotificationItem(
            id = "3",
            title = "Lunch Break Reminder",
            message = "Don't forget to grab lunch at the cafeteria! Lunch break is from 12:00 PM to 1:00 PM.",
            status = "Scheduled",
            sentTime = "Scheduled for 11:45 AM",
            recipients = 342
        ),
        NotificationItem(
            id = "4",
            title = "Evening Worship Service",
            message = "Join us for tonight's worship service at 7 PM in the main auditorium. Special guest worship leader David Wilson!",
            status = "Draft",
            sentTime = "Draft",
            recipients = 0
        ),
        NotificationItem(
            id = "5",
            title = "Feedback Request",
            message = "How was today's sessions? Please take a moment to provide feedback in the app. Your input helps us improve!",
            status = "Sent",
            sentTime = "Yesterday",
            recipients = 342,
            delivered = 312
        )
    )
}
