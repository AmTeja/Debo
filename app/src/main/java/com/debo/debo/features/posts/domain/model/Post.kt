package com.debo.debo.features.posts.domain.model

import com.debo.debo.features.authentication.domain.model.User
import com.google.firebase.Timestamp

sealed class Post(
    open val id: String = "",
    open val userId: String = "",
    open var user: User? = null,
    open val caption: String = "",
    open var likes: List<String> = emptyList(),
    open val createdAt: Timestamp = Timestamp.now(),
    open val updatedAt: Timestamp = Timestamp.now(),
) {
    constructor() : this("", "", null, "", emptyList(), Timestamp.now(), Timestamp.now())
}

enum class PostType {
    IMAGE,
    VIDEO,
    TEXT
}

data class ImagePost(
    val imageUrl: String = "",
    val imageWidth: Int = 0,
    val imageHeight: Int = 0,
    val imageRatio: Float = 0f,
    override val id: String = "",
    override val userId: String = "",
    override val caption: String = "",
    override val createdAt: Timestamp = Timestamp.now(),
    override val updatedAt: Timestamp = Timestamp.now(),
) : Post(id, userId, User(), caption, emptyList(), createdAt, updatedAt)

data class VideoPost(
    val videoUrl: String = "",
    val videoWidth: Int = 0,
    val videoHeight: Int = 0,
    val videoRatio: Float = 0f,
    override val id: String = "",
    override val userId: String = "",
    override val caption: String = "",
    override val createdAt: Timestamp = Timestamp.now(),
    override val updatedAt: Timestamp = Timestamp.now(),
) : Post(id, userId, User(), caption, emptyList(), createdAt, updatedAt)

data class TextPost(
    override val id: String = "",
    override val userId: String = "",
    override val caption: String = "",
    override val createdAt: Timestamp = Timestamp.now(),
    override val updatedAt: Timestamp = Timestamp.now(),
) : Post(id, userId, User(), caption, emptyList(), createdAt, updatedAt)
