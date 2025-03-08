package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResendVerificationEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    operator fun invoke(): Flow<Result<Unit>> = authRepository.resendVerificationEmail()
}