package com.debo.debo.features.posts.domain.repository

import com.debo.debo.features.posts.domain.model.Post
import com.debo.debo.util.Response
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<Response<List<Post>>>

    fun createPost(post: Post): Flow<Response<Boolean>>

    fun deletePost(postId: String): Flow<Response<Boolean>>

    fun updatePost(post: Post): Flow<Response<Boolean>>

    fun likePost(postId: String): Flow<Response<Boolean>>

    fun unlikePost(postId: String): Flow<Response<Boolean>>
}