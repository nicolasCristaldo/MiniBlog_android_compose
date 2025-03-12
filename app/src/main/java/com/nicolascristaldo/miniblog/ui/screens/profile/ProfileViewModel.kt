package com.nicolascristaldo.miniblog.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.nicolascristaldo.miniblog.data.repositories.UsersRepository
import com.nicolascristaldo.miniblog.domain.model.User
import com.nicolascristaldo.miniblog.domain.usecases.GetAuthUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAuthUserUseCase: GetAuthUserUseCase,
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user get() = _user.asStateFlow()

    private val _authUser = MutableStateFlow<FirebaseUser?>(null)
    val authUser get() = _authUser.asStateFlow()

    init {
        viewModelScope.launch {
            _authUser.value = getAuthUserUseCase()
        }
    }

    fun listenUserChanges(uid: String) = viewModelScope.launch {
        usersRepository.listenUserChanges(uid).collect { user ->
            _user.value = user
        }
    }

    fun formatDate(timestamp: Long?): String {
        return if(timestamp != null) {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
        else { "unknown" }
    }
}