package com.debo.debo.features.home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.debo.debo.features.posts.domain.model.Post
import com.debo.debo.features.posts.domain.use_cases.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel() {

    private val _selectedPost = mutableStateOf<Post?>(null)
    val selectedPost: State<Post?> = _selectedPost

    private val _isCommentPanelVisible = mutableStateOf(false)
    val isCommentPanelVisible: State<Boolean> = _isCommentPanelVisible

    fun toggleCommentPanelVisibility(post: Post?) {
        _selectedPost.value = post
        println(post?.comments)
        _isCommentPanelVisible.value = !_isCommentPanelVisible.value
    }

    fun openCommentPanel() {
        _isCommentPanelVisible.value = true
    }
}