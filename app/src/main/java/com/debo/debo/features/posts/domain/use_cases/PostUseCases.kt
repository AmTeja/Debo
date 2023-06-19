package com.debo.debo.features.posts.domain.use_cases

data class PostUseCases(
    val getPosts: GetPosts,
    val likePost: LikePost,
    val unlikePost: UnlikePost,
)
