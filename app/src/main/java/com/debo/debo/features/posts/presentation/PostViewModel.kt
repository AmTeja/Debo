package com.debo.debo.features.posts.presentation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debo.debo.features.posts.domain.model.ImagePost
import com.debo.debo.features.posts.domain.model.Post
import com.debo.debo.features.posts.domain.model.TextPost
import com.debo.debo.features.posts.domain.use_cases.PostUseCases
import com.debo.debo.util.Response
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val useCases: PostUseCases,
) : ViewModel() {
    private val _postsState = mutableStateOf<Response<List<Post>>>(Response.Initial)
    val postsState: State<Response<List<Post>>> = _postsState

    private val _createPostState = mutableStateOf<Response<Boolean>>(Response.Initial)
    val createPostState: State<Response<Boolean>> = _createPostState

    private val _captionState = mutableStateOf("")
    val captionState: State<String> = _captionState

    fun setCaption(caption: String) {
        _captionState.value = caption
    }

    private val _imageUrlState = mutableStateOf("")
    val imageUrlState: State<String> = _imageUrlState

    fun setImageUrl(imageUrl: String) {
        _imageUrlState.value = imageUrl
    }

    fun getPosts() {
        viewModelScope.launch {
            if (_postsState.value is Response.Success) return@launch
            useCases.getPosts().collect {
                _postsState.value = it
                Log.d(TAG, "getPosts: $it")
            }
        }
    }

    fun createPost() {
        viewModelScope.launch {

            if (imageUrlState.value.isEmpty()) {
                val post = TextPost(
                    caption = captionState.value,
                    createdAt = Timestamp.now(),
                    updatedAt = Timestamp.now(),
                )
                useCases.createPost(post).collect {
                    Log.d(TAG, "createPost: $it")
                }
                _captionState.value = ""
            } else {
                val post = ImagePost(
                    imageRatio = 16 / 9f,
                    caption = captionState.value,
                    imageUrl = imageUrlState.value,
                    createdAt = Timestamp.now(),
                    updatedAt = Timestamp.now(),
                )
                useCases.createPost(post).collect {
                    _createPostState.value = it
                }
                _captionState.value = ""
                _imageUrlState.value = ""
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