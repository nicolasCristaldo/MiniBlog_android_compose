package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.PostsRepository
import com.nicolascristaldo.miniblog.domain.model.Post
import javax.inject.Inject

class PublishPostUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    suspend operator fun invoke(post: Post) {
        postsRepository.createPost(post)
    }
}