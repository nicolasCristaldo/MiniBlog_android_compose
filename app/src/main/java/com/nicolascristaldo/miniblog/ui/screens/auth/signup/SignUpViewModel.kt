package com.nicolascristaldo.miniblog.ui.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import com.nicolascristaldo.miniblog.domain.model.User
import com.nicolascristaldo.miniblog.domain.usecases.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState get() = _uiState.asStateFlow()

    fun onNameChanged(newName: String) {
        _uiState.update { _uiState.value.copy(name = newName) }
    }

    fun onEmailChanged(newEmail: String) {
        _uiState.update { _uiState.value.copy(email = newEmail) }
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.update { _uiState.value.copy(password = newPassword) }
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _uiState.update { _uiState.value.copy(confirmPassword = newConfirmPassword) }
    }

    fun signUp() {
        val state = _uiState.value
        _uiState.value = state.copy(isLoading = true, error = null)

        viewModelScope.launch {
            repository.signUpWithEmail(state.email, state.password).collect { result ->
                _uiState.value = if (result.isSuccess) {
                    createUserProfile(name = state.name)
                    state.copy(isLoading = false, isSuccess = true)
                }
                else {
                    state.copy(isLoading = false, error = result.exceptionOrNull()?.message)
                }
            }
        }
    }

    private suspend fun createUserProfile(name: String) {
        val currentUser = repository.getAuthUser()
        currentUser?.let {
            val userProfile = User(
                uid = it.uid,
                name = name,
                email = it.email ?: "",
                createdAt = System.currentTimeMillis()
            )
            saveUserUseCase(userProfile)
        }
    }
}