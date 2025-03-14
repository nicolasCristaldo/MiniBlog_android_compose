package com.nicolascristaldo.miniblog.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.nicolascristaldo.miniblog.domain.model.Post
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostsRepository @Inject constructor(
    firestore: FirebaseFirestore
){
    private val postsCollection = firestore.collection("posts")

    suspend fun createPost(post: Post) {
        val postId = postsCollection.document().id
        val postWhitId = post.copy(id = postId)
        postsCollection.document(postId).set(postWhitId).await()
    }

    suspend fun getPosts(uid: String? = null): List<Post> {
        val query = if (uid != null) {
            postsCollection.whereEqualTo("userId", uid)
        } else {
            postsCollection
        }

        val snapshot = query.orderBy("createdAt", Query.Direction.DESCENDING).get().await()
        return snapshot.documents.mapNotNull {
            it.toObject(Post::class.java)
        }
    }

    suspend fun deletePost(postId: String) {
        postsCollection.document(postId).delete().await()
    }
}