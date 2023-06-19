package com.debo.debo.features.authentication.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debo.debo.features.authentication.domain.model.User
import com.debo.debo.features.authentication.domain.use_cases.AuthenticationUseCases
import com.debo.debo.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authUseCases: AuthenticationUseCases
) : ViewModel() {

    val isUserAuthenticated get() = authUseCases.isUserAuthenticated()

    private val _signInState = mutableStateOf<Response<Boolean>>(Response.Initial)
    val signInState: State<Response<Boolean>> = _signInState

    private val _signUpState = mutableStateOf<Response<Boolean>>(Response.Initial)
    val signUpState: State<Response<Boolean>> = _signUpState

    private val _signOutState = mutableStateOf<Response<Boolean>>(Response.Initial)
    val signOutState: State<Response<Boolean>> = _signOutState

    private val _firebaseAuthState = mutableStateOf<Boolean>(false)
    val firebaseAuthState: State<Boolean> = _firebaseAuthState

    private val _userState = mutableStateOf<User?>(null)
    val userState: State<User?> = _userState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authUseCases.login(email, password).collect {
                _signInState.value = it
            }
        }
    }

    fun signUp(email: String, password: String, username: String) {
        viewModelScope.launch {
            authUseCases.register(email, password, username).collect {
                _signUpState.value = it
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authUseCases.logout().collect {
                _signOutState.value = it
                if (it == Response.Success(true)) {
                    _signInState.value = Response.Success(false)
                }
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            _userState.value = authUseCases.getUser()
        }
    }

    fun getFirebaseAuthState() {
        viewModelScope.launch {
            authUseCases.firebaseAuthState().collect {
                _firebaseAuthState.value = it
            }
        }
    }
}