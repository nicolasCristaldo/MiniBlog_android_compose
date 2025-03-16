package com.nicolascristaldo.miniblog.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import com.nicolascristaldo.miniblog.domain.model.Post
import com.nicolascristaldo.miniblog.domain.model.User
import com.nicolascristaldo.miniblog.domain.usecases.GetAuthUserUseCase
import com.nicolascristaldo.miniblog.domain.usecases.GetUserUseCase
import com.nicolascristaldo.miniblog.domain.usecases.LogOutUseCase
import com.nicolascristaldo.miniblog.domain.usecases.PublishPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAuthUserUseCase: GetAuthUserUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val publishPostUseCase: PublishPostUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user get() = _user.asStateFlow()

    private val _authUser = MutableStateFlow<FirebaseUser?>(null)
    val authUser get() = _authUser.asStateFlow()

    private val _showLogOutDialog = MutableStateFlow(false)
    val showLogOutDialog get() = _showLogOutDialog

    init {
        viewModelScope.launch {
            authRepository.getAuthUserFlow().collect { user ->
                _authUser.value = user
            }
        }
    }

    fun loadUser() = viewModelScope.launch {
        val authUser = getAuthUserUseCase()
        authUser?.let {
            val user = getUserUseCase(it.uid)
            _user.value = user
        }
    }

    fun publishPost(content: String) = viewModelScope.launch {
        val currentUser = user.value
        if (currentUser != null) {
            val newPost = Post(
                userId = currentUser.uid,
                content = content
            )
            publishPostUseCase(newPost)
        }
    }

    fun changeLogOutDialogState(show: Boolean) {
        _showLogOutDialog.value = show
    }

    fun logOut() {
        logOutUseCase()
        _user.value = null
        _authUser.value = null
        _showLogOutDialog.value = false
    }
}