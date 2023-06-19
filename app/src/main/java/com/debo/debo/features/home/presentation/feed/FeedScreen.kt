package com.debo.debo.features.home.presentation.feed

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.debo.debo.features.core.presentation.Toast
import com.debo.debo.features.home.presentation.components.BottomNavBar
import com.debo.debo.features.home.presentation.components.BottomNavigationItem
import com.debo.debo.features.home.presentation.components.FeedContent
import com.debo.debo.features.posts.presentation.PostViewModel
import com.debo.debo.util.Response
import com.debo.debo.util.Screen
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel(),
    postViewModel: PostViewModel = hiltViewModel(),
    auth: FirebaseAuth,
) {
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
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.signOut()
            }) {
                when (viewModel.signOutState.value) {
                    is Response.Error -> {
                        Toast(message = "Something went wrong!")
                    }

                    Response.Initial -> {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout")
                    }

                    Response.Loading -> {
                        CircularProgressIndicator()
                    }

                    is Response.Success -> {
                        LaunchedEffect(key1 = true) {
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Screen.FeedScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            BottomNavBar(
                selectedItem = BottomNavigationItem.FEED,
                navController
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Log.d(TAG, "FeedScreen: ${viewModel.userState.value?.userId}")
            FeedContent(
                postViewModel,
                navController,
                userId = auth.currentUser!!.uid
            )
        }
    }
}