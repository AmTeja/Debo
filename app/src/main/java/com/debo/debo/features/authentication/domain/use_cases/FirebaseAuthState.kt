package com.debo.debo.features.authentication.domain.use_cases

import com.debo.debo.features.authentication.domain.repostiory.AuthenticationRepository
import javax.inject.Inject

class FirebaseAuthState @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke() = repository.getFirebaseAuthState()
}