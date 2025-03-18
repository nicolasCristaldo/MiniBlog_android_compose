package com.nicolascristaldo.miniblog.ui.screens.home

import com.google.firebase.auth.FirebaseUser
import com.nicolascristaldo.miniblog.domain.model.User

data class HomeUiState(
    val user: User? = null,
    val authUser: FirebaseUser? = null,
    val showLogOutDialog: Boolean = false,
    val postContent: String = "",
    val sendPostEnabled: Boolean = false
) {
    fun isValidPost() = postContent.length <= 200 && postContent.isNotBlank()
}
