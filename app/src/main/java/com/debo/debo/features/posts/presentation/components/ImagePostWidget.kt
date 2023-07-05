package com.debo.debo.features.posts.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.debo.debo.features.posts.domain.model.ImagePost
import com.debo.debo.features.posts.presentation.PostViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ImagePostWidget(
    post: ImagePost, userId: String,
    postViewModel: PostViewModel = hiltViewModel(),
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        ImagePostContent(post, userId, postViewModel)
    }
}

@Composable
fun ImagePostContent(post: ImagePost, userId: String, postViewModel: PostViewModel) {
    PostHeader(post = post)
    ImagePostBody(post = post)
    PostFooter(post = post, userId, postViewModel)
}

@Composable
fun ImagePostBody(post: ImagePost) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = post.caption,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
        ImagePostImage(post = post)
    }
}

@Composable
fun ImagePostImage(post: ImagePost) {

    GlideImage(
        imageModel = { post.imageUrl },
        Modifier
            .aspectRatio(post.imageRatio)
            .padding(horizontal = 4.dp)
    )
}
