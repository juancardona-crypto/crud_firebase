package com.example.crud_firebase.domain.usecase.task

import com.example.crud_firebase.domain.model.Task
import com.example.crud_firebase.domain.repository.TaskRepository

class UpdateTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Result<Unit> {
        if (task.id.isBlank()) {
            return Result.failure(IllegalArgumentException("La tarea no tiene id"))
        }
        if (task.title.isBlank()) {
            return Result.failure(IllegalArgumentException("El título no puede estar vacío"))
        }
        return taskRepository.updateTask(task)
    }
}