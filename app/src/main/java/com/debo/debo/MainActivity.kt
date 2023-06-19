package com.debo.debo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.debo.debo.features.authentication.presentation.login.LoginScreen
import com.debo.debo.features.authentication.presentation.register.RegisterScreen
import com.debo.debo.features.core.presentation.splash.SplashScreen
import com.debo.debo.features.home.presentation.feed.FeedScreen
import com.debo.debo.features.home.presentation.search.SearchScreen
import com.debo.debo.features.messages.presentation.MessagesScreen
import com.debo.debo.features.profile.presentation.profile.ProfileScreen
import com.debo.debo.ui.theme.DeboTheme
import com.debo.debo.util.Screen
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeboTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val auth = FirebaseAuth.getInstance()
                    DeboApp(navController, auth)
                }
            }
        }
    }
}

@Composable
fun DeboApp(navController: NavHostController, auth: FirebaseAuth) {
    NavHost(navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.LoginScreen.route) {
            EnterAnimation {
                LoginScreen(navController)
            }
        }
        composable(route = Screen.RegisterScreen.route) {
            EnterAnimation {
                RegisterScreen(navController)
            }
        }
        composable(route = Screen.FeedScreen.route) {
            FeedScreen(navController, auth = auth)
        }
        composable(route = Screen.SearchScreen.route) {
            SearchScreen(navController)
        }
        composable(route = Screen.MessagesScreen.route) {
            MessagesScreen(navController)
        }
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController)
        }
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun EnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            initialOffsetY = { -40 }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
        content = content,
        initiallyVisible = false
    )
}

