package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.PostsRepository
import com.nicolascristaldo.miniblog.domain.model.Post
import javax.inject.Inject

/**
 * Use case for publishing a new post to the data source.
 */
class PublishPostUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    /**
     * Publishes the specified post.
     * @param post The [Post] object to publish.
     */
    suspend operator fun invoke(post: Post) {
        postsRepository.createPost(post)
    }
}