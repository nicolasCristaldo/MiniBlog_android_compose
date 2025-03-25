package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.UsersRepository
import javax.inject.Inject

/**
 * Use case for updating a user's information.
 */
class UpdateUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    /**
     * Updates a user's information.
     * @param uid The UID of the user to update.
     * @param updates A map of field-value pairs to update.
     */
    suspend operator fun invoke(uid: String, updates: Map<String, Any>) =
        usersRepository.updateUser(uid, updates)
}