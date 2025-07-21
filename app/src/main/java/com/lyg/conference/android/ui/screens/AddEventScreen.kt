package com.lyg.conference.android.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.BitmapFactory
import android.net.Uri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var maxCapacity by remember { mutableStateOf("") }
    var registrationDeadline by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedStatus by remember { mutableStateOf("Draft") }
    
    val context = LocalContext.current
    val statusOptions = listOf("Draft", "Active", "Completed", "Cancelled")
    
    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }
    
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
                text = "Create New Event",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (name.isNotBlank() && location.isNotBlank() && 
                        startDate.isNotBlank() && endDate.isNotBlank() && 
                        maxCapacity.isNotBlank()) {
                        // TODO: Create event with viewModel
                        onNavigateBack()
                    }
                },
                enabled = name.isNotBlank() && location.isNotBlank()
            ) {
                Text("Create Event")
            }
        }
        
        // Form Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Event Image
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                onClick = { imagePickerLauncher.launch("image/*") }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    selectedImageUri?.let { uri ->
                        val bitmap = remember(uri) {
                            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                                BitmapFactory.decodeStream(inputStream)
                            }
                        }
                        bitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "Event Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    } ?: run {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Image,
                                contentDescription = "Add Image",
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Tap to add event banner",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            // Basic Information
            Text(
                text = "Event Details",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Event Name *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Event, contentDescription = null) },
                singleLine = true
            )
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                placeholder = { Text("Describe your event, its purpose, and what attendees can expect...") }
            )
            
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                singleLine = true
            )
            
            // Dates
            Text(
                text = "Schedule & Capacity",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = startDate,
                    onValueChange = { startDate = it },
                    label = { Text("Start Date *") },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("YYYY-MM-DD") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    singleLine = true
                )
                OutlinedTextField(
                    value = endDate,
                    onValueChange = { endDate = it },
                    label = { Text("End Date *") },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("YYYY-MM-DD") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    singleLine = true
                )
            }
            
            OutlinedTextField(
                value = maxCapacity,
                onValueChange = { maxCapacity = it },
                label = { Text("Maximum Capacity *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Group, contentDescription = null) },
                singleLine = true,
                placeholder = { Text("e.g., 500") }
            )
            
            OutlinedTextField(
                value = registrationDeadline,
                onValueChange = { registrationDeadline = it },
                label = { Text("Registration Deadline") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("YYYY-MM-DD") },
                leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                singleLine = true
            )
            
            // Status Selection
            Text(
                text = "Event Status",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                statusOptions.forEach { status ->
                    FilterChip(
                        onClick = { selectedStatus = status },
                        label = { Text(status) },
                        selected = selectedStatus == status
                    )
                }
            }
            
            // Tags
            OutlinedTextField(
                value = tags,
                onValueChange = { tags = it },
                label = { Text("Tags") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("youth, conference, worship, etc. (comma-separated)") },
                supportingText = { Text("Add tags to help categorize your event") }
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
