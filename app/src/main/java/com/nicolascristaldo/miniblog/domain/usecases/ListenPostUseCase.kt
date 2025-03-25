package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.PostsRepository
import javax.inject.Inject

/**
 * Use case for listening to changes in posts.
 */
class ListenPostUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    /**
     * Listens to changes in posts.
     * @param uid Optional user ID to filter posts by user.
     * @return A [Flow] emitting lists of [Post] objects.
     */
    operator fun invoke(uid: String? = null) = postsRepository.listenPostsChanges(uid)
}