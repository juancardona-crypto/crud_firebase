package com.example.crud_firebase.domain.usecase.auth

import com.example.crud_firebase.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Correo y contraseña son obligatorios"))
        }
        return authRepository.login(email, password)
    }
}