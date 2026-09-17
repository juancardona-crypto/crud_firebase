package com.example.crud_firebase.ui.screen.taskform

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.crud_firebase.domain.model.Task
import com.example.crud_firebase.ui.TaskViewModel

import com.example.crud_firebase.ui.screen.drafts.DraftViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    taskId: String? = null,
    viewModel: TaskViewModel = hiltViewModel(),
    draftViewModel: DraftViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // SitaskId no es null, buscamos la tarea en la lista actual
    val existingTask = remember(taskId, uiState.tasks) {
        uiState.tasks.find { it.id == taskId }
    }

    var title by remember { mutableStateOf(existingTask?.title ?: "") }
    var description by remember { mutableStateOf(existingTask?.description ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId == null) "Nueva Tarea" else "Editar Tarea") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    if (taskId == null) {
                        IconButton(onClick = {
                            draftViewModel.saveDraft(title, description)
                            onNavigateBack()
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Guardar Borrador")
                        }
                    }
                    IconButton(onClick = {
                        if (taskId == null) {
                            viewModel.addTask(title, description)
                        } else {
                            existingTask?.let {
                                viewModel.updateTask(it.copy(title = title, description = description))
                            }
                        }
                        onNavigateBack()
                    }) {
                        Icon(Icons.Default.Save, contentDescription = "Publicar/Guardar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        }
    }
}