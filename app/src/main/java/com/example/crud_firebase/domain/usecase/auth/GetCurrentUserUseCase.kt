package com.example.crud_firebase.domain.usecase.auth

import com.example.crud_firebase.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun observeAuthState(): Flow<String?> = authRepository.authState

    fun currentUserId(): String? = authRepository.currentUserId
}