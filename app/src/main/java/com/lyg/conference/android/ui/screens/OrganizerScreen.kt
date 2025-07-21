package com.lyg.conference.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrganizerScreen(
    onNavigateToSchedule: () -> Unit,
    onNavigateToScheduleManagement: () -> Unit,
    onNavigateToSpeakerManagement: () -> Unit,
    onNavigateToEventCreation: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Organizer Dashboard",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "Manage your Louisville Youth Group Conference",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Quick Stats
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Sessions",
                value = "12",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Attendees",
                value = "85",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Speakers",
                value = "8",
                modifier = Modifier.weight(1f)
            )
        }

        // Management Options
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(organizerMenuItems) { menuItem ->
                OrganizerMenuItem(
                    title = menuItem.title,
                    description = menuItem.description,
                    icon = menuItem.icon,
                    onClick = {
                        when (menuItem.title) {
                            "Schedule Management" -> onNavigateToScheduleManagement()
                            "Speaker Management" -> onNavigateToSpeakerManagement()
                            "Event Creation" -> onNavigateToEventCreation()
                            "Analytics" -> onNavigateToAnalytics()
                            "Notifications" -> onNavigateToNotifications()
                            "Settings" -> onNavigateToSettings()
                            "Schedule" -> onNavigateToSchedule()
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrganizerMenuItem(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private data class OrganizerMenuItem(
    val title: String,
    val description: String,
    val icon: ImageVector
)

private val organizerMenuItems = listOf(
    OrganizerMenuItem(
        title = "Schedule Management",
        description = "Create and manage conference sessions",
        icon = Icons.Default.CalendarToday
    ),
    OrganizerMenuItem(
        title = "Speaker Management",
        description = "Add and manage conference speakers",
        icon = Icons.Default.Group
    ),
    OrganizerMenuItem(
        title = "Event Creation",
        description = "Create new conferences and events",
        icon = Icons.Default.Add
    ),
    OrganizerMenuItem(
        title = "Analytics",
        description = "View attendance and engagement metrics",
        icon = Icons.Default.Analytics
    ),
    OrganizerMenuItem(
        title = "Notifications",
        description = "Send push notifications to attendees",
        icon = Icons.Default.Notifications
    ),
    OrganizerMenuItem(
        title = "Settings",
        description = "Configure conference settings",
        icon = Icons.Default.Settings
    )
)
