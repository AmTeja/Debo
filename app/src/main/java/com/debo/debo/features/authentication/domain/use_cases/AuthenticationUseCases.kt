package com.debo.debo.features.authentication.domain.use_cases

data class AuthenticationUseCases(
    val isUserAuthenticated: IsUserAuthenticated,
    val firebaseAuthState: FirebaseAuthState,
    val login: Login,
    val register: Register,
    val logout: Logout,
    val getUser: GetUser
)
