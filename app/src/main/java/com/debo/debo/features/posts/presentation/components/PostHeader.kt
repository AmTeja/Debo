package com.debo.debo.features.posts.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.debo.debo.features.core.presentation.components.ProfileAvatar
import com.debo.debo.features.posts.domain.model.Post

@Composable
fun PostHeader(post: Post) {
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