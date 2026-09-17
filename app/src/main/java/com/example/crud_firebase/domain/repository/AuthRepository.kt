package com.example.crud_firebase.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUserId: String?
    val authState: Flow<String?>

    suspend fun register(email: String, password: String): Result<String>
    suspend fun login(email: String, password: String): Result<String>
    fun logout()
}