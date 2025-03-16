package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.UsersRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(uid: String, updates: Map<String, Any>) =
        usersRepository.updateUser(uid, updates)
}