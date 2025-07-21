package com.lyg.conference.android.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lyg.conference.android.R
import com.lyg.conference.models.UserRole

@Composable
fun HomeDashboard(userRole: UserRole, onNavigate: (String) -> Unit) {
    val features = listOf(
        FeatureItem("Schedule", Icons.Filled.Event, "schedule_screen"),
        FeatureItem("Speakers", Icons.Filled.Person, "speaker_list_screen"),
        FeatureItem("My Tickets", Icons.Filled.ConfirmationNumber, "ticket_screen"),
        FeatureItem("Venue Map", Icons.Filled.Map, "map_screen"),
        FeatureItem("Messages", Icons.Filled.Chat, "message_center")
    ) + if (userRole == UserRole.ORGANIZER) {
        listOf(FeatureItem("Organizer Tools", Icons.Filled.AdminPanelSettings, "organizer_dashboard"))
    } else emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.lyg_primary_logo),
            contentDescription = "Louisville Youth Group Logo",
            modifier = Modifier
                .height(80.dp)
                .padding(bottom = 16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(16.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(features) { feature ->
                AnimatedVisibility(visible = true) {
                    FeatureCard(feature = feature, onClick = { onNavigate(feature.route) })
                }
            }
        }
    }
}

@Composable
fun FeatureCard(feature: FeatureItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(feature.icon, contentDescription = feature.title, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = feature.title, style = MaterialTheme.typography.labelLarge)
        }
    }
}

data class FeatureItem(val title: String, val icon: ImageVector, val route: String)
