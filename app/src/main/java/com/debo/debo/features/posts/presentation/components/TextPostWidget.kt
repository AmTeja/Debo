package com.debo.debo.features.posts.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.debo.debo.features.core.presentation.components.ProfileAvatar
import com.debo.debo.features.posts.domain.model.TextPost
import com.debo.debo.features.posts.presentation.PostViewModel


@Composable
fun TextPostWidget(
    post: TextPost,
    userId: String,
    postViewModel: PostViewModel = hiltViewModel()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(bottom = 16.dp),
        //zero radius to make it square
        shape = RoundedCornerShape(0.dp)
    ) {
        TextPostContent(post, userId, postViewModel)
    }
}

@Composable
fun TextPostContent(post: TextPost, userId: String, postViewModel: PostViewModel) {
    TextPostHeader(post = post)
    TextPostBody(post = post)
    PostFooter(post = post, userId, postViewModel)
}

@Composable
fun TextPostHeader(post: TextPost) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)

    ) {
        ProfileAvatar(imageUrl = post.user?.imageUrl, size = 36.dp)
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = post.user?.name ?: "",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "@${post.user?.userName}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun TextPostBody(post: TextPost) {
    Text(
        text = post.caption,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(16.dp)
    )
}



