package com.example.crud_firebase.data

import com.example.crud_firebase.domain.model.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TaskRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val tasksRef = db.collection("tasks")
    // Obtener todos los documentos una sola vez
    suspend fun getAllTasks(): List<Task> {
        val snapshot = tasksRef.get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(Task::class.java)?.copy(id = doc.id)
        }
    }
    // Obtener un documento por su ID
    suspend fun getTaskById(taskId: String): Task? {
        val doc = tasksRef.document(taskId).get().await()
        return doc.toObject(Task::class.java)?.copy(id = doc.id)

    }
    // Consulta filtrada: solo tareas completadas, ordenadas por fecha
    suspend fun getCompletedTasks(): List<Task> {
        val snapshot = tasksRef
            .whereEqualTo("completed", true)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(20)
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(Task::class.java)?.copy(id =
            it.id) }
    }
    // Escuchar cambios en tiempo real (ideal para UI reactiva en Compose)
    fun observeTasks(): Flow<List<Task>> = callbackFlow {
        val listener = tasksRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val tasks = snapshot?.documents?.mapNotNull {
                it.toObject(Task::class.java)?.copy(id = it.id)
            } ?: emptyList()
            trySend(tasks)
        }
        awaitClose { listener.remove() }
    }
    suspend fun addTask(task: Task): String {
        val ref = tasksRef.add(task).await()
        return ref.id
    }
    suspend fun updateTask(taskId: String, changes: Map<String, Any>) {
        tasksRef.document(taskId).update(changes).await()
    }
    suspend fun deleteTask(taskId: String) {
        tasksRef.document(taskId).delete().await()
    }
}