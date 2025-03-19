package com.nicolascristaldo.miniblog.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.nicolascristaldo.miniblog.domain.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostsRepository @Inject constructor(
    firestore: FirebaseFirestore
){
    private val postsCollection = firestore.collection("posts")

    suspend fun createPost(post: Post): Unit = withContext(Dispatchers.IO) {
        val postId = postsCollection.document().id
        val postWhitId = post.copy(id = postId)
        postsCollection.document(postId).set(postWhitId).await()
    }

    fun listenPostsChanges(uid: String? = null) = callbackFlow {
        val query = if (uid != null) postsCollection.whereEqualTo("userId", uid)
            else postsCollection

        val listener = query.orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(30)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val posts = snapshot?.documents?.mapNotNull { it.toObject(Post::class.java) } ?: emptyList()
                trySend(posts).isSuccess
            }
        awaitClose { listener.remove() }
    }

    suspend fun deletePost(postId: String): Unit = withContext(Dispatchers.IO) {
        postsCollection.document(postId).delete().await()
    }
}