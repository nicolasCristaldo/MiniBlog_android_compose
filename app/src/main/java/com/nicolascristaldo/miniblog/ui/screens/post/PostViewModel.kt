package com.nicolascristaldo.miniblog.ui.screens.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascristaldo.miniblog.domain.model.PostWithUser
import com.nicolascristaldo.miniblog.domain.usecases.DeletePostUseCase
import com.nicolascristaldo.miniblog.domain.usecases.GetUserUseCase
import com.nicolascristaldo.miniblog.domain.usecases.ListenPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val listenPostUseCase: ListenPostUseCase
): ViewModel() {
    private val _posts = MutableStateFlow<List<PostWithUser>>(emptyList())
    val posts get() = _posts.asStateFlow()

    private val _postToDelete = MutableStateFlow<String?>(null)
    val postToDelete get() = _postToDelete.asStateFlow()

    fun listenPostsChanges(uid: String? = null) = viewModelScope.launch {
        listenPostUseCase(uid)
            .catch { _posts.value = emptyList() }
            .collect { posts ->
                val postWithUser = posts.map { post ->
                    val user = getUserUseCase(post.userId)
                    PostWithUser(post, user!!)
                }
                _posts.value = postWithUser
            }
    }

    fun deletePost() = viewModelScope.launch {
        if(_postToDelete.value != null) {
            deletePostUseCase(_postToDelete.value!!)
            _postToDelete.value = null
        }
    }

    fun changePostToDeleteValue(postId: String? = null) {
        _postToDelete.value = postId
    }
}