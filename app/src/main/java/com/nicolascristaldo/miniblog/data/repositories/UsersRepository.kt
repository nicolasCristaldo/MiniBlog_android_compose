package com.nicolascristaldo.miniblog.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.nicolascristaldo.miniblog.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository for managing user operations in Firestore.
 */
class UsersRepository @Inject constructor(
    firestore: FirebaseFirestore
) {
    private val usersCollection = firestore.collection("users")

    /**
     * Creates a new user in Firestore.
     * @param user The [User] object to be created.
     */
    suspend fun createUser(user: User): Unit = withContext(Dispatchers.IO) {
        usersCollection.document(user.uid).set(user).await()
    }

    /**
     * Retrieves a user from Firestore by their UID.
     * @param uid The UID of the user to retrieve.
     * @return The [User] object if found, or null if not found.
     */
    suspend fun getUser(uid: String): User? = withContext(Dispatchers.IO) {
        usersCollection.document(uid).get().await().toObject(User::class.java)
    }

    /**
     * Updates a user's information in Firestore.
     * @param uid The UID of the user to update.
     * @param updates A map of field-value pairs to update.
     */
    suspend fun updateUser(uid: String, updates: Map<String, Any>): Unit = withContext(Dispatchers.IO) {
        usersCollection.document(uid).update(updates).await()
    }

    /**
     * Provides a flow emitting user changes based on the provided UID.
     * @param uid The UID of the user to listen for changes.
     * @return A [Flow] emitting [User] objects.
     */
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