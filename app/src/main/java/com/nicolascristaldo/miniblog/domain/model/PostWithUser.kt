package com.nicolascristaldo.miniblog.domain.model

data class PostWithUser(
    val post: Post,
    val user: User
)
