package com.debo.debo.features.authentication.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.debo.debo.features.authentication.presentation.AuthenticationViewModel
import com.debo.debo.features.core.presentation.Toast
import com.debo.debo.util.Response
import com.debo.debo.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val screenHeight = LocalConfiguration.current.screenHeightDp
        Column(
            modifier = Modifier
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Surface(
                modifier = Modifier
                    .height((screenHeight * 0.3).dp)
                    .fillMaxWidth()
            ) {
                Column(

                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Welcome Back", style = MaterialTheme.typography.displaySmall.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(
                        text = "Sign in to continue",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                val emailState = remember {
                    mutableStateOf("")
                }
                val passwordState = remember {
                    mutableStateOf("")
                }

                OutlinedTextField(value = emailState.value, onValueChange = {
                    emailState.value = it
                }, modifier = Modifier.padding(12.dp), label = {
                    Text(text = "Email")
                })

                OutlinedTextField(value = passwordState.value, onValueChange = {
                    passwordState.value = it
                }, modifier = Modifier.padding(12.dp), label = {
                    Text(text = "Password")
                }, visualTransformation = PasswordVisualTransformation()
                )

                Button(onClick = {
                    viewModel.signIn(emailState.value, passwordState.value)
                }, modifier = Modifier.padding(8.dp)) {
                    when (val response = viewModel.signInState.value) {
                        is Response.Initial -> {
                            Text("Sign In")
                        }

                        is Response.Loading -> {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(4.dp)
                            )
                        }

                        is Response.Success -> {
                            if (response.data) {
                                Text("Signed In")
                                LaunchedEffect(key1 = true) {
                                    navController.navigate(Screen.FeedScreen.route) {
                                        popUpTo(Screen.LoginScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            } else {
                                Text("Sign In")
                                Toast(message = "Invalid Credentials")
                            }
                        }

                        is Response.Error -> {
                            Text("Sign In")
                            Toast(message = response.message)
                        }
                    }
                }

                Text(
                    text = "New user? Register here.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.RegisterScreen.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }

}