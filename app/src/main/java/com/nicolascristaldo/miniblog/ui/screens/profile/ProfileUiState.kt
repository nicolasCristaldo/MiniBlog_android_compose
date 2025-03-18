package com.nicolascristaldo.miniblog.ui.screens.profile

import com.google.firebase.auth.FirebaseUser
import com.nicolascristaldo.miniblog.domain.model.User

data class ProfileUiState(
    val user: User? = null,
    val authUser: FirebaseUser? = null,
    val isEditing: Boolean = false,
    val editingName: String = "",
    val editingBio: String = ""
) {
    fun isValidName(): Boolean = editingName.length in 3..20
    fun isValidBio(): Boolean = editingBio.length <= 100
    fun isValidInputForm(): Boolean = isValidName() && isValidBio()
}

