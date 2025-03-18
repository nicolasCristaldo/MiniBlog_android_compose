package com.nicolascristaldo.miniblog.ui.screens.auth.signup

import android.util.Patterns

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
) {
    fun isValidName(): Boolean = name.length in 3..20
    fun isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isValidPassword(): Boolean = password.length >= 6
    fun arePasswordsEqual(): Boolean = password == confirmPassword
    fun validateInputForm(): Boolean = isValidName() &&
            isValidEmail() && isValidPassword() && arePasswordsEqual()
}
