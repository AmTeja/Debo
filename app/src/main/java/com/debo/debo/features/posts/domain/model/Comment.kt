package com.debo.debo.features.posts.domain.model

data class Comment(
    val id: String? = "",
    val postId: String = "",
    val userId: String = "",
    val comment: String = "",
    val createdAt: String = "",
    val displayName : String = "",
    val userImage : String = "",
    val username : String = ""
)
