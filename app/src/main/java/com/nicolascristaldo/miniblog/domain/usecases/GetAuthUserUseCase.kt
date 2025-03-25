package com.nicolascristaldo.miniblog.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import javax.inject.Inject

/**
 * Use case for retrieving the currently authenticated user.
 */
class GetAuthUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    /**
     * Executes the use case to fetch the current authenticated user.
     * @return The current [FirebaseUser] if authenticated, or null if not.
     */
    suspend operator fun invoke(): FirebaseUser? = authRepository.getAuthUser()
}