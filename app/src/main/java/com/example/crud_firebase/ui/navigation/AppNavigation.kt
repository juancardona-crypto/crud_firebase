package com.example.crud_firebase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.crud_firebase.ui.auth.AuthViewModel
import com.example.crud_firebase.ui.screen.login.LoginScreen
import com.example.crud_firebase.ui.screen.register.RegisterScreen
import com.example.crud_firebase.ui.screen.taskform.TaskFormScreen
import com.example.crud_firebase.ui.screen.tasklist.TaskListScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object TaskList : Screen("task_list")
    object TaskForm : Screen("task_form/{taskId}") {
        fun createRoute(taskId: String?) = "task_form/${taskId ?: "new"}"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val authUiState by authViewModel.uiState.collectAsState()

    val startDestination = if (authUiState.userId != null) {
        Screen.TaskList.route
    } else {
        Screen.Login.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = {
                    navController.navigate(Screen.TaskList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Screen.TaskList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.TaskList.route) {
            TaskListScreen(
                onNavigateToForm = { taskId ->
                    navController.navigate(Screen.TaskForm.createRoute(taskId))
                },
                onNavigateToDrafts = { /* TODO */ },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.TaskList.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = Screen.TaskForm.route,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            TaskFormScreen(
                taskId = if (taskId == "new") null else taskId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}