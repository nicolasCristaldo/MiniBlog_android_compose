package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for resending a verification email to a user.
 */
class ResendVerificationEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    /**
     * Resends a verification email to the user.
     * @return A [Flow] emitting [Result] indicating the success or failure of the operation.
     */
    operator fun invoke(): Flow<Result<Unit>> = authRepository.resendVerificationEmail()
}