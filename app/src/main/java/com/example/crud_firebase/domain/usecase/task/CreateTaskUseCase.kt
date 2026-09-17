package com.example.crud_firebase.domain.usecase.task

import com.example.crud_firebase.domain.model.Task
import com.example.crud_firebase.domain.repository.TaskRepository

class CreateTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Result<String> {
        if (task.title.isBlank()) {
            return Result.failure(IllegalArgumentException("El título no puede estar vacío"))
        }
        if (task.ownerId.isBlank()) {
            return Result.failure(IllegalArgumentException("El usuario no está autenticado"))
        }
        return taskRepository.createTask(task)
    }
}