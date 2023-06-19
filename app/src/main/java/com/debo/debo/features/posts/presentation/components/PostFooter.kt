package com.debo.debo.features.posts.presentation.components

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.debo.debo.features.posts.domain.model.Post
import com.debo.debo.features.posts.presentation.PostViewModel
import com.debo.debo.util.timeAgo

@Composable
fun PostFooter(post: Post, userId: String, viewModel: PostViewModel) {

    Log.d(ContentValues.TAG, "TextPostFooter: $userId")
    val isLiked = remember { mutableStateOf(post.likes.contains(userId)) }
    val likesCount = remember { mutableIntStateOf(post.likes.size) }

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = post.createdAt.timeAgo,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "${likesCount.intValue}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                IconButton(onClick = {
                    isLiked.value = !isLiked.value
                    if (isLiked.value) {
                        likesCount.intValue++
                        viewModel.likePost(post.id)
                    } else {
                        likesCount.intValue--
                        viewModel.unlikePost(post.id)
                    }
                }) {
                    Icon(
                        imageVector = if (isLiked.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
