package com.nicolascristaldo.miniblog.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.nicolascristaldo.miniblog.data.repositories.UsersRepository
import com.nicolascristaldo.miniblog.domain.model.User
import com.nicolascristaldo.miniblog.domain.usecases.GetAuthUserUseCase
import com.nicolascristaldo.miniblog.domain.usecases.ResendVerificationEmailUseCase
import com.nicolascristaldo.miniblog.domain.usecases.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing authentication operations.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val resendVerificationEmailUseCase: ResendVerificationEmailUseCase,
    private val getCurrentUserUseCase: GetAuthUserUseCase,
    private val usersRepository: UsersRepository,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {
    private val _userState = MutableStateFlow<FirebaseUser?>(null)
    val userState get() = _userState.asStateFlow()

    private val _resendTimer = MutableStateFlow(0)
    val resendTimer get() =  _resendTimer.asStateFlow()

    private val _verificationResult = MutableStateFlow<Result<Unit>?>(null)
    val verificationResult get() = _verificationResult.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val navigationEvent: SharedFlow<String> get() = _navigationEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _userState.value = getCurrentUserUseCase()
        }
    }

    /**
     * Starts the email verification check loop.
     */
    fun startEmailVerificationCheck() = viewModelScope.launch {
        while (_userState.value?.isEmailVerified != true) {
            delay(3000)
            val updatedUser = getCurrentUserUseCase()
            _userState.value = updatedUser
            if (updatedUser?.isEmailVerified == true) {
                _navigationEvent.emit("home")
                break
            }
        }
    }

    /**
     * Resends the verification email.
     */
    fun resendVerificationEmail() = viewModelScope.launch {
        if (_resendTimer.value == 0) {
            resendVerificationEmailUseCase().collect { result ->
                _verificationResult.value = result
            }
            startResetTimer()
        }
    }

    /**
     * Starts the countdown timer for resending the verification email.
     */
    fun startResetTimer() = viewModelScope.launch {
        val countdown = 60
        _resendTimer.value = countdown
        for (i in countdown downTo 1) {
            delay(1000)
            _resendTimer.value = i
        }
        _resendTimer.value = 0
    }

    /**
     * Checks if a user profile exists and creates one if not.
     */
    fun checkAndCreateUserProfile() = viewModelScope.launch {
        val authUser = getCurrentUserUseCase()
        if (authUser != null) {
            val user = usersRepository.getUser(authUser.uid)
            if (user == null) {
                val userProfile = User(
                    uid = authUser.uid,
                    name = "User",
                    email = authUser.email ?: "",
                    createdAt = System.currentTimeMillis()
                )
                saveUserUseCase(userProfile)
            }
        }
    }
}