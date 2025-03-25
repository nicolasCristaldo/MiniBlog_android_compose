package com.nicolascristaldo.miniblog.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import com.nicolascristaldo.miniblog.data.repositories.UsersRepository
import com.nicolascristaldo.miniblog.domain.usecases.GetAuthUserUseCase
import com.nicolascristaldo.miniblog.domain.usecases.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing profile screen state and logic.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAuthUserUseCase: GetAuthUserUseCase,
    private val usersRepository: UsersRepository,
    private val updateUserUseCase: UpdateUserUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(authUser = getAuthUserUseCase()) }
            authRepository.getAuthUserFlow().collect { user ->
                _uiState.update { it.copy(authUser = user) }
            }
        }
    }

    /**
     * Updates the visibility of the edit profile dialog.
     * @param editingState The new visibility state of the dialog.
     */
    fun changeEditingState(editingState: Boolean) {
        _uiState.update { it.copy(isEditing = editingState) }
    }

    /**
     * Updates the value of the editingName field of the UI state.
     * @param newName The new name value to set.
     */
    fun changeEditingName(newName: String) {
        _uiState.update { it.copy(editingName = newName) }
    }

    /**
     * Updates the value of the editingBio field of the UI state.
     * @param newBio The new bio value to set.
     */
    fun changeEditingBio(newBio: String) {
        _uiState.update { it.copy(editingBio = newBio) }
    }

    /**
     * Listens for changes in the user's profile and updates the UI.
     * @param uid The user ID to listen for changes.
     */
    fun listenUserChanges(uid: String) = viewModelScope.launch {
        usersRepository.listenUserChanges(uid)
            .catch { _uiState.update { it.copy(user = null) } }
            .collect { user -> _uiState.update { it.copy(user = user) } }
    }

    /**
     * Updates the user's profile with the current editing values.
     */
    fun updateUser() = viewModelScope.launch {
        val updates = mapOf(
            "name" to _uiState.value.editingName,
            "bio" to _uiState.value.editingBio
        )

        _uiState.value.authUser?.uid?.let { uid ->
            updateUserUseCase(uid, updates)
            _uiState.update { it.copy(isEditing = false) }
        }
    }
}