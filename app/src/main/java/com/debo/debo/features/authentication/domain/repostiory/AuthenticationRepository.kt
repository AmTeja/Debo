package com.debo.debo.features.authentication.domain.repostiory

import com.debo.debo.features.authentication.domain.model.User
import com.debo.debo.util.Response
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun isUserAuthenticated(): Boolean

    fun getFirebaseAuthState(): Flow<Boolean>

    fun getCurrentUser(): User?

    fun login(email: String, password: String): Flow<Response<Boolean>>

    fun logout(): Flow<Response<Boolean>>

    fun register(email: String, password: String, userName: String): Flow<Response<Boolean>>
}