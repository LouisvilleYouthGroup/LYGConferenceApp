package com.lyg.conference.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lyg.conference.android.ui.screens.AttendeeScreen
import com.lyg.conference.android.ui.screens.LoginScreen
import com.lyg.conference.android.ui.screens.OrganizerScreen
import com.lyg.conference.android.ui.screens.ScheduleScreen

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
    }
}
