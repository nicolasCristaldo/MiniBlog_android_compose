package com.nicolascristaldo.miniblog.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.nicolascristaldo.miniblog.domain.model.User
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun saveUser(user: User) {
        firestore.collection("users")
            .document(user.uid)
            .set(user)
    }

    fun getUser(
        uid: String,
        onSuccess: (User?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                onSuccess(user)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }
}