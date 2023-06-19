package com.debo.debo.features.core.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.debo.debo.R
import com.debo.debo.features.authentication.presentation.AuthenticationViewModel
import com.debo.debo.util.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {
    val authValue = viewModel.isUserAuthenticated

    LaunchedEffect(key1 = true) {
        delay(700)
        if (authValue) {
            viewModel.getUser()
            navController.navigate(Screen.FeedScreen.route) {
                popUpTo(Screen.SplashScreen.route) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(Screen.SplashScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            stringResource(R.string.app_name).uppercase(),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}