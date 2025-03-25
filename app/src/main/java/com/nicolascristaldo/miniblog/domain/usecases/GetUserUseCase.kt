package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.UsersRepository
import com.nicolascristaldo.miniblog.domain.model.User
import javax.inject.Inject

/**
 * Use case for retrieving a user by their UID.
 */
class GetUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    /**
     * Retrieves a user by their UID.
     * @param uid The UID of the user to retrieve.
     * @return The [User] object if found, or null if not found.
     */
    suspend operator fun invoke(uid: String): User? = usersRepository.getUser(uid)
}