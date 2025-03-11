package com.nicolascristaldo.miniblog.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.nicolascristaldo.miniblog.domain.model.Post
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostsRepository @Inject constructor(
    firestore: FirebaseFirestore
){
    private val postsCollection = firestore.collection("posts")

    suspend fun createPost(post: Post) {
        postsCollection.add(post).await()
    }

    fun getPosts(uid: String? = null): Flow<List<Post>> = callbackFlow {
        val query = if (uid != null) {
            postsCollection.whereEqualTo("userId", uid)
        } else {
            postsCollection
        }

        val listener = query.orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val posts = snapshot?.documents?.mapNotNull {
                    it.toObject(Post::class.java)
                } ?: emptyList()

                trySend(posts).isSuccess
            }
        awaitClose { listener.remove() }
    }
}