package com.lyg.conference.android.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Work
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
import com.lyg.conference.models.Speaker
import com.lyg.conference.viewmodels.SpeakerManagementViewModel
import org.koin.androidx.compose.koinViewModel
import android.graphics.BitmapFactory
import android.net.Uri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSpeakerScreen(
    onNavigateBack: () -> Unit,
    viewModel: SpeakerManagementViewModel = koinViewModel()
) {
    var name by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var organization by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var expertise by remember { mutableStateOf("") }
    var linkedIn by remember { mutableStateOf("") }
    var twitter by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val operationResult by viewModel.operationResult.collectAsState()
    val imageUploadProgress by viewModel.imageUploadProgress.collectAsState()
    
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
                text = "Add New Speaker",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank()) {
                        val expertiseList = expertise.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                        val socialLinks = mutableMapOf<String, String>()
                        if (linkedIn.isNotBlank()) socialLinks["linkedin"] = linkedIn
                        if (twitter.isNotBlank()) socialLinks["twitter"] = twitter
                        if (website.isNotBlank()) socialLinks["website"] = website
                        
                        viewModel.createSpeaker(
                            Speaker(
                                id = "speaker_${System.currentTimeMillis()}",
                                name = name,
                                title = title,
                                organization = organization,
                                email = email,
                                bio = bio,
                                expertise = expertiseList,
                                socialLinks = socialLinks
                            )
                        )
                    }
                },
                enabled = !isLoading && name.isNotBlank() && email.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
                } else {
                    Text("Create Speaker")
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
            // Profile Image Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
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
                                    contentDescription = "Speaker Photo",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        } ?: run {
                            Icon(
                                Icons.Default.PhotoCamera,
                                contentDescription = "Add Photo",
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        // Upload progress overlay
                        imageUploadProgress?.let { progress ->
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(progress = progress)
                                }
                            }
                        }
                    }
                }
                
                Text(
                    text = "Tap to add photo",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            
            // Basic Information
            Text(
                text = "Basic Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                singleLine = true
            )
            
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Job Title") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Work, contentDescription = null) },
                singleLine = true
            )
            
            OutlinedTextField(
                value = organization,
                onValueChange = { organization = it },
                label = { Text("Organization") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Business, contentDescription = null) },
                singleLine = true
            )
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                singleLine = true
            )
            
            // Biography
            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("Biography") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                maxLines = 6,
                placeholder = { Text("Tell us about this speaker's background and experience...") }
            )
            
            // Expertise
            OutlinedTextField(
                value = expertise,
                onValueChange = { expertise = it },
                label = { Text("Areas of Expertise") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Youth Ministry, Leadership, Worship, etc. (comma-separated)") },
                supportingText = { Text("Separate multiple areas with commas") }
            )
            
            // Social Links
            Text(
                text = "Social Media & Links",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            
            OutlinedTextField(
                value = linkedIn,
                onValueChange = { linkedIn = it },
                label = { Text("LinkedIn Profile") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("https://linkedin.com/in/username") },
                singleLine = true
            )
            
            OutlinedTextField(
                value = twitter,
                onValueChange = { twitter = it },
                label = { Text("Twitter/X Handle") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("@username") },
                singleLine = true
            )
            
            OutlinedTextField(
                value = website,
                onValueChange = { website = it },
                label = { Text("Personal Website") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("https://example.com") },
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
