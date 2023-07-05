package com.debo.debo.features.posts.domain.model

import com.debo.debo.features.authentication.domain.model.User
import com.google.firebase.Timestamp

sealed class Post(
    open var id: String? = "",
    open var userId: String = "",
    open var user: User? = null,
    open val caption: String = "",
    open var comments: List<Comment> = emptyList(),
    open var likes: List<String> = emptyList(),
    var postType: String? = null,
    open val createdAt: Timestamp = Timestamp.now(),
    open val updatedAt: Timestamp = Timestamp.now(),
) {
//    constructor() : this("", "", null, "", emptyList(), emptyList(), null, Timestamp.now(), Timestamp.now())
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
    override var id: String? = "",
    override var userId: String = "",
    override val caption: String = "",
    override val createdAt: Timestamp = Timestamp.now(),
    override val updatedAt: Timestamp = Timestamp.now(),
) : Post(id, userId, User(), caption, emptyList(), emptyList(),"IMAGE", createdAt, updatedAt)

data class VideoPost(
    val videoUrl: String = "",
    val videoWidth: Int = 0,
    val videoHeight: Int = 0,
    val videoRatio: Float = 0f,
    override var id: String? = "",
    override var userId: String = "",
    override val caption: String = "",
    override val createdAt: Timestamp = Timestamp.now(),
    override val updatedAt: Timestamp = Timestamp.now(),
) : Post(id, userId, User(), caption, emptyList(), emptyList(), "VIDEO", createdAt, updatedAt)

data class TextPost(
    override var id: String? = "",
    override var userId: String = "",
    override val caption: String = "",
    override val createdAt: Timestamp = Timestamp.now(),
    override val updatedAt: Timestamp = Timestamp.now(),
) : Post(id, userId, User(), caption, emptyList(), emptyList(), "TEXT", createdAt, updatedAt)
