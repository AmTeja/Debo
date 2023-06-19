package com.debo.debo.features.authentication.domain.use_cases

import com.debo.debo.features.authentication.domain.repostiory.AuthenticationRepository
import javax.inject.Inject

class Register @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(email: String, password: String, username: String) =
        repository.register(email, password, username)
}