package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.PostsRepository
import javax.inject.Inject

/**
 * Use case for deleting a post.
 */
class DeletePostUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    /**
     * Executes the use case to delete a post.
     * @param postId The ID of the post to be deleted.
     */
    suspend operator fun invoke(postId: String) = postsRepository.deletePost(postId)
}