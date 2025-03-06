package com.nicolascristaldo.miniblog.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.nicolascristaldo.miniblog.domain.usecases.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    getCurrentUserUseCase: GetCurrentUserUseCase
): ViewModel() {
    private val _userState = MutableStateFlow<FirebaseUser?>(null)
    val userState get() = _userState.asStateFlow()

    init {
        viewModelScope.launch {
            _userState.value = getCurrentUserUseCase()
        }
    }
}