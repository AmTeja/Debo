package com.debo.debo.features.profile.domain.model

data class Profile(
    var name: String = "",
    var userName: String = "",
    var userId: String = "",
    var email: String = "",
    var imageUrl: String = "",
    var following: List<String> = emptyList(),
    var followers: List<String> = emptyList(),
    var totalPosts: String = "",
    var bio: String = ""
)
