package com.nicolascristaldo.miniblog.domain.model

data class User(
    val uid: String = "",
    val name: String = "",
    val bio: String = "",
    val email: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
