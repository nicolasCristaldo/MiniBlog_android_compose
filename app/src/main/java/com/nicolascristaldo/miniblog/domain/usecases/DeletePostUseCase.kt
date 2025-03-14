package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.PostsRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    suspend operator fun invoke(postId: String) = postsRepository.deletePost(postId)
}