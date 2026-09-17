package com.example.crud_firebase.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_firebase.domain.usecase.auth.GetCurrentUserUseCase
import com.example.crud_firebase.domain.usecase.auth.LoginUserUseCase
import com.example.crud_firebase.domain.usecase.auth.LogoutUserUseCase
import com.example.crud_firebase.domain.usecase.auth.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val userId: String? = null,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentUserUseCase.observeAuthState().collect { uid ->
                _uiState.update { it.copy(userId = uid) }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, isSuccess = false) }
            loginUserUseCase(email, password)
                .onSuccess { uid ->
                    _uiState.update { it.copy(isLoading = false, userId = uid, isSuccess = true) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, isSuccess = false) }
            registerUserUseCase(email, password)
                .onSuccess { uid ->
                    _uiState.update { it.copy(isLoading = false, userId = uid, isSuccess = true) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun logout() {
        logoutUserUseCase()
        _uiState.update { AuthUiState() }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}