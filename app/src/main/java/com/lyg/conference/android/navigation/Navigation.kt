package com.lyg.conference.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lyg.conference.android.ui.screens.*

@Composable
fun LYGConferenceNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { userRole ->
                    when (userRole) {
                        com.lyg.conference.models.UserRole.ATTENDEE -> {
                            navController.navigate("attendee_dashboard") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                        com.lyg.conference.models.UserRole.ORGANIZER -> {
                            navController.navigate("organizer_dashboard") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                }
            )
        }
        
        composable("attendee_dashboard") {
            AttendeeScreen(
                onNavigateToSchedule = {
                    navController.navigate("schedule")
                }
            )
        }
        
        composable("organizer_dashboard") {
            OrganizerScreen(
                onNavigateToSchedule = {
                    navController.navigate("schedule")
                },
                onNavigateToScheduleManagement = {
                    navController.navigate("schedule_management")
                },
                onNavigateToSpeakerManagement = {
                    navController.navigate("speaker_management")
                },
                onNavigateToEventCreation = {
                    navController.navigate("event_creation")
                },
                onNavigateToAnalytics = {
                    navController.navigate("analytics")
                },
                onNavigateToNotifications = {
                    navController.navigate("notifications")
                },
                onNavigateToSettings = {
                    navController.navigate("settings")
                }
            )
        }
        
        composable("schedule") {
            ScheduleScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        // New dashboard feature screens
        composable("schedule_management") {
            ScheduleManagementScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onAddSession = {
                    navController.navigate("add_session")
                }
            )
        }
        
        composable("speaker_management") {
            SpeakerManagementScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onAddSpeaker = {
                    navController.navigate("add_speaker")
                }
            )
        }
        
        composable("event_creation") {
            EventCreationScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onAddEvent = {
                    navController.navigate("add_event")
                }
            )
        }
        
        composable("analytics") {
            AnalyticsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("notifications") {
            NotificationsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onCreateNotification = {
                    // TODO: Navigate to create notification screen when implemented
                }
            )
        }
        
        composable("settings") {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Add/Edit screens
        composable("add_session") {
            AddSessionScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("add_speaker") {
            AddSpeakerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("add_event") {
            AddEventScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
