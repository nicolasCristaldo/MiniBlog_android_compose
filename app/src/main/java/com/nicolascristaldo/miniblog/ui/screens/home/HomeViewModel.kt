package com.nicolascristaldo.miniblog.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import com.nicolascristaldo.miniblog.domain.model.Post
import com.nicolascristaldo.miniblog.domain.usecases.GetAuthUserUseCase
import com.nicolascristaldo.miniblog.domain.usecases.GetUserUseCase
import com.nicolascristaldo.miniblog.domain.usecases.LogOutUseCase
import com.nicolascristaldo.miniblog.domain.usecases.PublishPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing home screen state and logic.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAuthUserUseCase: GetAuthUserUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val publishPostUseCase: PublishPostUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.getAuthUserFlow().collect { authUser ->
                _uiState.update { _uiState.value.copy(authUser = authUser) }
            }
        }
    }

    /**
     * Updates the visibility of the log out dialog.
     * @param show The new visibility state of the dialog.
     */
    fun changeLogOutDialogState(show: Boolean) {
        _uiState.update { _uiState.value.copy(showLogOutDialog = show) }
    }

    /**
     * Updates the content of the post.
     * @param content The new content of the post.
     */
    fun changePostContent(content: String) {
        _uiState.update { _uiState.value.copy(postContent = content) }
    }

    /**
     * Loads the user's information from the data source.
     */
    fun loadUser() = viewModelScope.launch {
        val authUser = getAuthUserUseCase()
        authUser?.let {
            val user = getUserUseCase(it.uid)
            _uiState.update { _uiState.value.copy(user = user) }
        }
    }

    /**
     * Publishes a new post with the current content.
     */
    fun publishPost() = viewModelScope.launch {
        if (_uiState.value.user != null) {
            val newPost = Post(
                userId = _uiState.value.user!!.uid,
                content = _uiState.value.postContent
            )
            publishPostUseCase(newPost)
        }
    }

    /**
     * Logs out the current user.
     */
    fun logOut() {
        logOutUseCase()
        _uiState.value = HomeUiState()
    }
}