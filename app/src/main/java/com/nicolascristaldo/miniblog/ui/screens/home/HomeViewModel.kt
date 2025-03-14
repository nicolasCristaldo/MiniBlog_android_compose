package com.nicolascristaldo.miniblog.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val publishPostUseCase: PublishPostUseCase
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user get() = _user.asStateFlow()

    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut get() = _isLoggedOut

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

    fun logOut() {
        logOutUseCase()
        _isLoggedOut.value = true
    }
}