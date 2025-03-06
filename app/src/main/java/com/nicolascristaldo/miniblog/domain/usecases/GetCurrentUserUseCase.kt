package com.nicolascristaldo.miniblog.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): FirebaseUser? = authRepository.getCurrentUser()
}