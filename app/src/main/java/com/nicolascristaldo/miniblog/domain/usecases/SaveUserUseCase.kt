package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.UsersRepository
import com.nicolascristaldo.miniblog.domain.model.User
import javax.inject.Inject

/**
 * Use case for saving a user to the data source.
 */
class SaveUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    /**
     * Saves the specified user.
     * @param user The [User] object to save.
     */
    suspend operator fun invoke(user: User) {
        usersRepository.createUser(user)
    }
}