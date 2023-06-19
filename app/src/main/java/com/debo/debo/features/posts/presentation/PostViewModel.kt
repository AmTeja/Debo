package com.debo.debo.features.posts.presentation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debo.debo.features.posts.domain.model.Post
import com.debo.debo.features.posts.domain.use_cases.PostUseCases
import com.debo.debo.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val useCases: PostUseCases
) : ViewModel() {
    private val _postsState = mutableStateOf<Response<List<Post>>>(Response.Initial)
    val postsState: State<Response<List<Post>>> = _postsState

    fun getPosts() {
        viewModelScope.launch {
            if (_postsState.value is Response.Success) return@launch
            useCases.getPosts().collect {
                _postsState.value = it
                Log.d(TAG, "getPosts: $it")
            }
        }
    }

    fun likePost(postId: String) {
        viewModelScope.launch {
            useCases.likePost(postId).collect {
                Log.d(TAG, "likePost: $it")
            }
        }
    }

    fun unlikePost(postId: String) {
        viewModelScope.launch {
            useCases.unlikePost(postId).collect {
                Log.d(TAG, "unlikePost: $it")
            }
        }
    }
}