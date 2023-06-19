package com.debo.debo.features.posts.domain.use_cases

import com.debo.debo.features.posts.domain.repository.PostRepository
import javax.inject.Inject

class UnlikePost @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId: String) = repository.unlikePost(postId)
}