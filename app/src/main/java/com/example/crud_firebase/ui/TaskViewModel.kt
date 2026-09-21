package com.example.crud_firebase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_firebase.domain.model.Task
import com.example.crud_firebase.domain.repository.AuthRepository
import com.example.crud_firebase.domain.usecase.task.CreateTaskUseCase
import com.example.crud_firebase.domain.usecase.task.DeleteTaskUseCase
import com.example.crud_firebase.domain.usecase.task.GetTasksUseCase
import com.example.crud_firebase.domain.usecase.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaskListUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskListUiState())
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    init {
        authRepository.currentUserId?.let { observeTasks(it) }
    }

    private fun observeTasks(ownerId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getTasksUseCase(ownerId)
                .catch { e -> _uiState.update { it.copy(errorMessage = e.message, isLoading = false) } }
                .collect { tasks ->
                    _uiState.update { it.copy(tasks = tasks, isLoading = false, errorMessage = null) }
                }
        }
    }

    fun addTask(title: String, description: String = "") {
        val ownerId = authRepository.currentUserId ?: return
        if (title.isBlank() || description.isBlank()) return
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            createTaskUseCase(Task(title = title, description = description, ownerId = ownerId, createdAt = now, updatedAt = now))
                .onFailure { e -> _uiState.update { it.copy(errorMessage = e.message) } }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task.copy(updatedAt = System.currentTimeMillis()))
                .onFailure { e -> _uiState.update { it.copy(errorMessage = e.message) } }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task.id)
        }
    }
}