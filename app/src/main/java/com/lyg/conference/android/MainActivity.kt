package com.lyg.conference.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.lyg.conference.android.di.appModule
import com.lyg.conference.android.ui.navigation.AuthNavHost
import com.lyg.conference.android.ui.screens.AttendeeScreen
import com.lyg.conference.android.ui.screens.HomeDashboard
import com.lyg.conference.android.ui.screens.OrganizerScreen
import com.lyg.conference.android.ui.theme.LYGConferenceTheme
import com.lyg.conference.models.UserRole
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Koin
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }
        enableEdgeToEdge()
        setContent {
            LYGConferenceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LYGConferenceApp()
                }
            }
        }
    }
}

@Composable
fun LYGConferenceApp() {
    var userRole by remember { mutableStateOf<UserRole?>(null) }
    var currentRoute by remember { mutableStateOf<String?>(null) }
    if (userRole == null) {
        AuthNavHost(
            onAuthSuccess = { role ->
                userRole = role
                currentRoute = null
            }
        )
    } else {
        if (currentRoute == null) {
            HomeDashboard(userRole = userRole!!, onNavigate = { route -> currentRoute = route })
        } else {
            when (currentRoute) {
                "organizer_dashboard" -> OrganizerScreen(
                    onNavigateToSchedule = {},
                    onNavigateToScheduleManagement = {},
                    onNavigateToSpeakerManagement = {},
                    onNavigateToEventCreation = {},
                    onNavigateToAnalytics = {},
                    onNavigateToNotifications = {},
                    onNavigateToSettings = {},
                    onBack = { currentRoute = null }
                )
                else -> AttendeeScreen(
                    onNavigateToSchedule = {},
                    onBack = { currentRoute = null }
                )
            }
        }
    }
}
