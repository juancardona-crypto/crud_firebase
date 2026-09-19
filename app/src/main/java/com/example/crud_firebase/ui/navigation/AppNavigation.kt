package com.example.crud_firebase.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.crud_firebase.ui.auth.AuthViewModel
import com.example.crud_firebase.ui.screen.login.LoginScreen
import com.example.crud_firebase.ui.screen.register.RegisterScreen
import com.example.crud_firebase.ui.screen.drafts.DraftsScreen
import com.example.crud_firebase.ui.screen.taskform.TaskFormScreen
import com.example.crud_firebase.ui.screen.tasklist.TaskListScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object TaskList : Screen("task_list")
    object TaskForm : Screen("task_form/{taskId}") {
        fun createRoute(taskId: String?) = "task_form/${taskId ?: "new"}"
    }
    object Drafts : Screen("drafts")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val authUiState by authViewModel.uiState.collectAsState()

    // RF03: Cierre de sesión reactivo
    LaunchedEffect(authUiState.userId, authUiState.isInitializing) {
        if (!authUiState.isInitializing) {
            if (authUiState.userId == null) {
                // Si no hay usuario, forzar ir al Login y borrar historial
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            } else {
                // Si hay usuario y estamos en login/registro, ir a la lista
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                if (currentRoute == Screen.Login.route || currentRoute == Screen.Register.route) {
                    navController.navigate(Screen.TaskList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }
        }
    }

    if (authUiState.isInitializing) {
        // Pantalla de carga inicial mientras se comprueba la sesión
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        NavHost(
            navController = navController,
            startDestination = if (authUiState.userId != null) Screen.TaskList.route else Screen.Login.route
        ) {
            composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.TaskList.route) {
            TaskListScreen(
                onNavigateToForm = { taskId ->
                    navController.navigate(Screen.TaskForm.createRoute(taskId))
                },
                onNavigateToDrafts = { navController.navigate(Screen.Drafts.route) }
            )
        }
            composable(Screen.Drafts.route) {
                DraftsScreen(
                    onNavigateBack = { navController.popBackStack() }
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
}