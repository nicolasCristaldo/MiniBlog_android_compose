package com.nicolascristaldo.miniblog.domain.model

data class Post(
    val id: String = "",
    val userId: String = "",
    val content: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
