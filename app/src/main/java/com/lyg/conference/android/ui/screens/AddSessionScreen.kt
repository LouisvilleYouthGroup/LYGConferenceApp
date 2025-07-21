package com.lyg.conference.android.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyg.conference.models.Session
import com.lyg.conference.viewmodels.ScheduleManagementViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.koin.androidx.compose.koinViewModel
import android.graphics.BitmapFactory
import android.net.Uri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSessionScreen(
    onNavigateBack: () -> Unit,
    viewModel: ScheduleManagementViewModel = koinViewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var requiresRegistration by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var isStartDateTime by remember { mutableStateOf(true) }
    
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val operationResult by viewModel.operationResult.collectAsState()
    
    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }
    
    // Handle operation result
    LaunchedEffect(operationResult) {
        operationResult?.let { result ->
            when (result) {
                is com.lyg.conference.viewmodels.OperationResult.Success -> {
                    onNavigateBack()
                }
                is com.lyg.conference.viewmodels.OperationResult.Error -> {
                    // Error is handled by error state
                }
            }
            viewModel.clearOperationResult()
        }
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
                text = "Add New Session",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (title.isNotBlank() && location.isNotBlank() && 
                        startDate.isNotBlank() && startTime.isNotBlank() &&
                        endDate.isNotBlank() && endTime.isNotBlank()) {
                        
                        try {
                            val startInstant = LocalDateTime.parse("${startDate}T${startTime}:00")
                                .toInstant(TimeZone.UTC)
                            val endInstant = LocalDateTime.parse("${endDate}T${endTime}:00")
                                .toInstant(TimeZone.UTC)
                            
                            viewModel.createSession(
                                Session(
                                    id = "session_${System.currentTimeMillis()}",
                                    title = title,
                                    description = description,
                                    startTime = startInstant,
                                    endTime = endInstant,
                                    location = location,
                                    capacity = capacity.toIntOrNull(),
                                    requiresRegistration = requiresRegistration
                                )
                            )
                        } catch (e: Exception) {
                            // Handle date parsing error
                        }
                    }
                },
                enabled = !isLoading && title.isNotBlank() && location.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
                } else {
                    Text("Create Session")
                }
            }
        }
        
        // Error display
        error?.let { errorMessage ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = errorMessage,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
        
        // Form Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Session Image
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
                                contentDescription = "Session Image",
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
                                text = "Tap to add session image",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            // Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Session Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )
            
            // Location
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                singleLine = true
            )
            
            // Date and Time Section
            Text(
                text = "Schedule",
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
                    label = { Text("Start Date") },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("YYYY-MM-DD") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    singleLine = true
                )
                OutlinedTextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("Start Time") },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("HH:MM") },
                    leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                    singleLine = true
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = endDate,
                    onValueChange = { endDate = it },
                    label = { Text("End Date") },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("YYYY-MM-DD") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    singleLine = true
                )
                OutlinedTextField(
                    value = endTime,
                    onValueChange = { endTime = it },
                    label = { Text("End Time") },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("HH:MM") },
                    leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                    singleLine = true
                )
            }
            
            // Capacity
            OutlinedTextField(
                value = capacity,
                onValueChange = { capacity = it },
                label = { Text("Capacity (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                singleLine = true
            )
            
            // Registration Required
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = requiresRegistration,
                    onCheckedChange = { requiresRegistration = it }
                )
                Text(
                    text = "Requires Registration",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
