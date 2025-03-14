package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.PostsRepository
import com.nicolascristaldo.miniblog.domain.model.Post
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    suspend operator fun invoke(uid: String? = null): List<Post> = postsRepository.getPosts(uid)
}