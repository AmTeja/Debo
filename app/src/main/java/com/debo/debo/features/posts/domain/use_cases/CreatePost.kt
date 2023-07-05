package com.debo.debo.features.posts.domain.use_cases

import com.debo.debo.features.posts.domain.model.Post
import com.debo.debo.features.posts.domain.repository.PostRepository
import javax.inject.Inject

class CreatePost @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(post: Post) = repository.createPost(post)
}