package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import javax.inject.Inject

/**
 * Use case for logging out a user.
 */
class LogOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    /**
     * Logs out the currently authenticated user.
     */
    operator fun invoke() = authRepository.logOut()
}