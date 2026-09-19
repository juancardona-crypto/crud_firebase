package com.example.crud_firebase.data.repository

import com.example.crud_firebase.data.mapper.toDocument
import com.example.crud_firebase.data.mapper.toDomain
import com.example.crud_firebase.data.remote.model.TaskDocument
import com.example.crud_firebase.domain.model.Task
import com.example.crud_firebase.domain.repository.TaskRepository
import com.example.crud_firebase.util.toUserFriendlyMessage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : TaskRepository {

    private val tasksRef = db.collection("tasks")

    override fun observeTasks(ownerId: String): Flow<List<Task>> = callbackFlow {
        val query = tasksRef.whereEqualTo("ownerId", ownerId)
            .orderBy("createdAt", Query.Direction.DESCENDING)

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(Exception(error.toUserFriendlyMessage()))
                return@addSnapshotListener
            }
            val tasks = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(TaskDocument::class.java)?.toDomain()
            } ?: emptyList()
            trySend(tasks)
        }
        awaitClose { listener.remove() }
    }

    override suspend fun createTask(task: Task): Result<String> {
        return try {
            val ref = tasksRef.add(task.toDocument()).await()
            Result.success(ref.id)
        } catch (e: Exception) {
            Result.failure(Exception(e.toUserFriendlyMessage()))
        }
    }

    override suspend fun updateTask(task: Task): Result<Unit> {
        return try {
            val document = task.toDocument()
            val updates = mapOf(
                "title" to document.title,
                "description" to document.description,
                "completed" to document.completed,
                "updatedAt" to System.currentTimeMillis()
            )
            tasksRef.document(task.id).update(updates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception(e.toUserFriendlyMessage()))
        }
    }

    override suspend fun deleteTask(taskId: String): Result<Unit> {
        return try {
            tasksRef.document(taskId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception(e.toUserFriendlyMessage()))
        }
    }
}