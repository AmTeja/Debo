package com.debo.debo.features.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.debo.debo.features.core.presentation.Toast
import com.debo.debo.features.posts.domain.model.ImagePost
import com.debo.debo.features.posts.domain.model.TextPost
import com.debo.debo.features.posts.domain.model.VideoPost
import com.debo.debo.features.posts.presentation.PostViewModel
import com.debo.debo.features.posts.presentation.components.TextPostWidget
import com.debo.debo.util.Response

@Composable
fun FeedContent(
    viewModel: PostViewModel,
    navController: NavController,
    userId: String
) {
    when (viewModel.postsState.value) {
        is Response.Error -> {
            Toast(message = "Something went wrong!")
        }

        Response.Initial -> {
            viewModel.getPosts()
        }

        Response.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }

        is Response.Success -> {
            val posts = (viewModel.postsState.value as Response.Success).data
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    items(posts.size) { index ->
                        when (val post = posts[index]) {

                            is TextPost -> {
                                TextPostWidget(post, userId)
                            }

                            is ImagePost -> TODO()
                            is VideoPost -> TODO()
                        }
                    }
                }
            }
        }
    }
}