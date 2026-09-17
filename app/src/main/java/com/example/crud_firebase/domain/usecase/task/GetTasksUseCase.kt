package com.example.crud_firebase.domain.usecase.task

import com.example.crud_firebase.domain.model.Task
import com.example.crud_firebase.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(ownerId: String): Flow<List<Task>> {
        return taskRepository.observeTasks(ownerId)
    }
}