package com.debo.debo.features.profile.presentation.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.debo.debo.features.profile.presentation.profile.components.ProfileError
import com.debo.debo.features.profile.presentation.profile.components.ProfileLoading
import com.debo.debo.features.profile.presentation.profile.components.ProfilePage
import com.debo.debo.util.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp;

    LaunchedEffect(key1 = true) {
        viewModel.getProfile()
    }

    when (val response = viewModel.profileState.value) {
        is Response.Error -> ProfileError(navController)
        Response.Initial -> {
            ProfileLoading(navController)
        }

        Response.Loading -> ProfileLoading(navController)
        is Response.Success -> ProfilePage(response.data, navController)
    }
}