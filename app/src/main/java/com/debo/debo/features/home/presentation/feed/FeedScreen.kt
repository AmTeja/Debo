package com.debo.debo.features.home.presentation.feed

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.debo.debo.R
import com.debo.debo.features.authentication.presentation.AuthenticationViewModel
import com.debo.debo.features.core.presentation.components.ProfileAvatar
import com.debo.debo.features.home.presentation.FeedViewModel
import com.debo.debo.features.home.presentation.components.BottomNavBar
import com.debo.debo.features.home.presentation.components.BottomNavigationItem
import com.debo.debo.features.home.presentation.components.FeedContent
import com.debo.debo.features.posts.presentation.PostViewModel
import com.debo.debo.features.profile.presentation.profile.ProfileViewModel
import com.debo.debo.util.Response
import com.debo.debo.util.Screen
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    postViewModel: PostViewModel = hiltViewModel(),
    feedViewModel: FeedViewModel = hiltViewModel(),
    auth: FirebaseAuth,
) {

    LaunchedEffect(key1 = true ) {
        profileViewModel.getProfile()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(3.dp),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(R.string.app_name))
                    }
                },
                actions = {
                    Column (
                        modifier = Modifier.padding(end = 16.dp).fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        ){
                        Surface {
                            when (val response = profileViewModel.profileState.value) {
                                is Response.Error -> Icon(
                                    imageVector = Icons.Default.ErrorOutline,
                                    contentDescription = null,
                                )

                                Response.Initial -> CircularProgressIndicator()
                                Response.Loading -> CircularProgressIndicator()
                                is Response.Success -> ProfileAvatar(
                                    imageUrl = response.data.imageUrl,
                                    size = 32.dp,
                                    modifier = Modifier.clickable {
                                        navController.navigate(Screen.ProfileScreen.route)
                                    }
                                )
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.CreatePostScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Log.d(TAG, "FeedScreen: ${viewModel.userState.value?.userId}")
            Box {
                FeedContent(
                    postViewModel,
                    navController,
                    userId = auth.currentUser!!.uid
                )
                if (feedViewModel.isCommentPanelVisible.value) {
                    CommentsPanel(feedViewModel)
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsPanel(feedViewModel: FeedViewModel) {

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            initialValue = SheetValue.Expanded,
            skipPartiallyExpanded = false,
            confirmValueChange = { sheetValue ->
                if (sheetValue == SheetValue.Hidden) {
                    feedViewModel.toggleCommentPanelVisibility(null)
                }
                true
            }
        )
    )

    BottomSheetScaffold(
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(text = "Comments", style = MaterialTheme.typography.headlineMedium)
                when (val post = feedViewModel.selectedPost.value) {
                    null -> Text(text = "No post selected")
                    else -> {
                        post.comments.forEach { comment ->
                            Text(text = comment.comment)
                        }
                    }
                }
            }
        },
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        sheetContainerColor = MaterialTheme.colorScheme.surface
    ) {

    }

}
