package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.logOut()
}