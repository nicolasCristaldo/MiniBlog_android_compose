package com.nicolascristaldo.miniblog.ui.screens.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascristaldo.miniblog.domain.model.PostWithUser
import com.nicolascristaldo.miniblog.domain.usecases.DeletePostUseCase
import com.nicolascristaldo.miniblog.domain.usecases.GetPostsUseCase
import com.nicolascristaldo.miniblog.domain.usecases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val deletePostUseCase: DeletePostUseCase
): ViewModel() {
    private val _posts = MutableStateFlow<List<PostWithUser>>(emptyList())
    val posts get() = _posts.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts(uid: String? = null) = viewModelScope.launch {
        val posts = getPostsUseCase(uid)
        val postWithUser = posts.map { post ->
            val user = getUserUseCase(post.userId)
            PostWithUser(post, user!!)
        }
        _posts.value = postWithUser
    }

    fun deletePost(postId: String) = viewModelScope.launch {
        deletePostUseCase(postId)
    }

    fun formatDate(timestamp: Long?): String {
        return if(timestamp != null) {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
        else { "unknown" }
    }
}