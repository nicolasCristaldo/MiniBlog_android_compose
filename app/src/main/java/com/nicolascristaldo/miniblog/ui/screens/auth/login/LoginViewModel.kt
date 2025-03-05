package com.nicolascristaldo.miniblog.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState get() = _uiState.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun login() {
        val state = _uiState.value
        _uiState.value = state.copy(isLoading = true, error = null)

        viewModelScope.launch {
            repository.logInWithEmail(state.email, state.password).collect { result ->
                _uiState.value = if (result.isSuccess) {
                    state.copy(isLoading = false, isSuccess = true)
                } else {
                    state.copy(isLoading = false, error = getAuthErrorMessage(result.exceptionOrNull()))
                }
            }
        }
    }

    private fun getAuthErrorMessage(e: Throwable?): String {
        return when ((e as? FirebaseAuthException)?.errorCode) {
            "ERROR_USER_NOT_FOUND" -> "User not found."
            "ERROR_WRONG_PASSWORD" -> "Wrong password."
            "ERROR_USER_DISABLED" -> "User disabled."
            else -> e?.localizedMessage ?: "Unknown error."
        }
    }
}