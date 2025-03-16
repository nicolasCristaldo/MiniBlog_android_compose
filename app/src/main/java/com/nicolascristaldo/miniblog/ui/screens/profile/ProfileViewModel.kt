package com.nicolascristaldo.miniblog.ui.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import com.nicolascristaldo.miniblog.data.repositories.UsersRepository
import com.nicolascristaldo.miniblog.domain.model.User
import com.nicolascristaldo.miniblog.domain.usecases.GetAuthUserUseCase
import com.nicolascristaldo.miniblog.domain.usecases.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAuthUserUseCase: GetAuthUserUseCase,
    private val usersRepository: UsersRepository,
    private val updateUserUseCase: UpdateUserUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user get() = _user.asStateFlow()

    private val _authUser = MutableStateFlow<FirebaseUser?>(null)
    val authUser get() = _authUser.asStateFlow()

    private val _isEditing = MutableStateFlow(false)
    val isEditing get() = _isEditing.asStateFlow()

    init {
        viewModelScope.launch {
            _authUser.value = getAuthUserUseCase()
            authRepository.getAuthUserFlow().collect{ user ->
                _authUser.value = user
            }
        }
    }

    fun listenUserChanges(uid: String) = viewModelScope.launch {
        usersRepository.listenUserChanges(uid)
            .catch { e ->
                Log.e("ProfileViewModel", "Error listening to user changes", e)
                _user.value = null
            }
            .collect { user ->
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

    fun updateUser(name: String, bio: String) = viewModelScope.launch {
        val updates = mutableMapOf<String, Any>()
        if (name.isNotBlank()) {
            updates["name"] = name
        }
        if (bio.isNotBlank()) {
            updates["bio"] = bio
        }
        if (updates.isNotEmpty()) {
            updateUserUseCase(_authUser.value!!.uid, updates)
        }
    }

    fun changeEditingState(editingState: Boolean) {
        _isEditing.value = editingState
    }
}