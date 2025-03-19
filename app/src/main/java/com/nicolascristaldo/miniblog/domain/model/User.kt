package com.nicolascristaldo.miniblog.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class User(
    val uid: String = "",
    val name: String = "",
    val bio: String = "",
    val email: String = "",
    val createdAt: Long = System.currentTimeMillis()
) {
    fun formatDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date(createdAt))
    }
}
