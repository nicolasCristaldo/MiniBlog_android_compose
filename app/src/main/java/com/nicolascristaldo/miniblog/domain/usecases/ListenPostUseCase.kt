package com.nicolascristaldo.miniblog.domain.usecases

import com.nicolascristaldo.miniblog.data.repositories.PostsRepository
import javax.inject.Inject

class ListenPostUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    operator fun invoke(uid: String? = null) = postsRepository.listenPostsChanges(uid)
}