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

/**
 * Repository for managing posts in Firestore.
 */
class PostsRepository @Inject constructor(
    firestore: FirebaseFirestore
){
    private val postsCollection = firestore.collection("posts")

    /**
     * Creates a new post in Firestore.
     * @param post The [Post] object to be created.
     */
    suspend fun createPost(post: Post): Unit = withContext(Dispatchers.IO) {
        val postId = postsCollection.document().id
        val postWhitId = post.copy(id = postId)
        postsCollection.document(postId).set(postWhitId).await()
    }

    /**
     * Retrieves a list of posts from Firestore.
     * @param uid Optional user ID to filter posts by user.
     * @return A list of [Post] objects.
     */
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

    /**
     * Deletes a post from Firestore.
     * @param postId The ID of the post to be deleted.
     */
    suspend fun deletePost(postId: String): Unit = withContext(Dispatchers.IO) {
        postsCollection.document(postId).delete().await()
    }
}