package com.debo.debo.features.posts.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
        shape = RoundedCornerShape(0.dp)
    ) {
        TextPostContent(post, userId, postViewModel)
    }
}

@Composable
fun TextPostContent(post: TextPost, userId: String, postViewModel: PostViewModel) {
    PostHeader(post = post)
    TextPostBody(post = post)
    PostFooter(post = post, userId, postViewModel)
}



@Composable
fun TextPostBody(post: TextPost) {
    Text(
        text = post.caption,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(16.dp)
    )
}



