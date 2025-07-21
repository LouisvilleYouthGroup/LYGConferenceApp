package com.lyg.conference.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lyg.conference.android.ui.screens.LoginScreen
import com.lyg.conference.android.ui.screens.RegisterScreen
import com.lyg.conference.models.UserRole

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login")
    object Register : AuthScreen("register")
}

@Composable
fun AuthNavHost(
    onAuthSuccess: (UserRole) -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AuthScreen.Login.route
    ) {
        composable(AuthScreen.Login.route) {
            LoginScreen(
                onLoginSuccess = onAuthSuccess,
                onRegister = { navController.navigate(AuthScreen.Register.route) }
            )
        }
        composable(AuthScreen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { userRole ->
                    // After registration, proceed to app or back to login
                    onAuthSuccess(userRole)
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
