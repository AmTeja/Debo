package com.debo.debo.features.authentication.presentation.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val emailState = remember {
                mutableStateOf("")
            }
            val passwordState = remember {
                mutableStateOf("")
            }
            val usernameState = remember {
                mutableStateOf("")
            }

            Surface(
                modifier = Modifier
                    .height((screenHeight * 0.3).dp)
                    .fillMaxWidth()
            ) {
                Column(

                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Welcome To Debo",
                        style = MaterialTheme.typography.displaySmall.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(
                        text = "",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }

            OutlinedTextField(value = usernameState.value, onValueChange = {
                usernameState.value = it
            }, modifier = Modifier.padding(12.dp), label = {
                Text(text = "Username")
            })

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
                viewModel.signUp(emailState.value, passwordState.value, usernameState.value)
            }, modifier = Modifier.padding(8.dp)) {
                Text("Register")
                when (val response = viewModel.signUpState.value) {
                    is Response.Initial -> {

                    }

                    is Response.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is Response.Success -> {
                        if (response.data) {
                            LaunchedEffect(key1 = true) {
                                navController.navigate(Screen.FeedScreen.route) {
                                    popUpTo(Screen.LoginScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        } else {
                            Toast(message = "Unable to register!")
                        }
                    }

                    is Response.Error -> {
                        Toast(message = response.message)
                    }
                }
            }

            Text(
                text = "Already an user? Login here.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.LoginScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}