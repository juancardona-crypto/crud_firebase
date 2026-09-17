package com.example.crud_firebase.domain.usecase.task

import com.example.crud_firebase.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: String): Result<Unit> {
        if (taskId.isBlank()) {
            return Result.failure(IllegalArgumentException("Id de tarea inválido"))
        }
        return taskRepository.deleteTask(taskId)
    }
}