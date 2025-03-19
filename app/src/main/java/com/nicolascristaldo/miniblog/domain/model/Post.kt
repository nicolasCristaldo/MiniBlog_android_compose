package com.nicolascristaldo.miniblog.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Post(
    val id: String = "",
    val userId: String = "",
    val content: String = "",
    val createdAt: Long = System.currentTimeMillis()
) {
    fun formatDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(createdAt))
    }
}
