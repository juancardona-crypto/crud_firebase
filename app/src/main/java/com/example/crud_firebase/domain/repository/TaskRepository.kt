package com.example.crud_firebase.domain.repository

import com.example.crud_firebase.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun observeTasks(ownerId: String): Flow<List<Task>>
    suspend fun createTask(task: Task): Result<String>
    suspend fun updateTask(task: Task): Result<Unit>
    suspend fun deleteTask(taskId: String): Result<Unit>
}