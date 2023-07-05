package com.debo.debo.features.posts.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.debo.debo.R
import com.debo.debo.features.core.presentation.Toast
import com.debo.debo.util.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    navController: NavController,
    postViewModel: PostViewModel = hiltViewModel()
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.create_post))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
            )
        },

        ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            Column {
                OutlinedTextField(value = postViewModel.captionState.value, onValueChange = {
                    postViewModel.setCaption(it)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight * 0.3).dp)
                    .padding(12.dp), label = {
                    Text(text = "Caption")
                })
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = postViewModel.imageUrlState.value, onValueChange = {
                    postViewModel.setImageUrl(it)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp), label = {
                    Text(text = "Image URL")
                })
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        if (postViewModel.createPostState.value is Response.Loading) return@Button
                        postViewModel.createPost()

                    }) {
                        when (val response = postViewModel.createPostState.value) {
                            is Response.Error -> {
                                Toast(message = response.message)
                                Text("Post")
                            }

                            Response.Initial -> {
                                Text("Post")
                            }

                            Response.Loading -> {
                                Text("Posting...")
                            }

                            is Response.Success -> {
                                Text("Posted!")
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}