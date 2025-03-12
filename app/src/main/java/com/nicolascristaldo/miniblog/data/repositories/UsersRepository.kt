package com.nicolascristaldo.miniblog.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.nicolascristaldo.miniblog.domain.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRepository @Inject constructor(
    firestore: FirebaseFirestore
) {
    private val usersCollection = firestore.collection("users")

    suspend fun createUser(user: User) {
        usersCollection.document(user.uid).set(user).await()
    }

    suspend fun getUser(uid: String): User? {
        return usersCollection.document(uid).get().await().toObject(User::class.java)
    }

    suspend fun updateUser(uid: String, updates: Map<String, Any>) {
        usersCollection.document(uid).update(updates).await()
    }

    fun listenUserChanges(uid: String): Flow<User?> = callbackFlow {
        val listener = usersCollection.document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val user = snapshot?.toObject(User::class.java)
                trySend(user).isSuccess
            }
        awaitClose { listener.remove() }
    }
}