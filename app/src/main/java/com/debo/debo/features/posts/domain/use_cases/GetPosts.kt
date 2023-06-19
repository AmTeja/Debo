package com.debo.debo.features.posts.domain.use_cases

import com.debo.debo.features.posts.domain.repository.PostRepository
import javax.inject.Inject

class GetPosts @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke() = repository.getPosts()
}