package com.example.crud_firebase.domain.usecase.task

import com.example.crud_firebase.domain.repository.TaskRepository

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: String): Result<Unit> {
        if (taskId.isBlank()) {
            return Result.failure(IllegalArgumentException("Id de tarea inválido"))
        }
        return taskRepository.deleteTask(taskId)
    }
}