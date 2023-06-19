package com.debo.debo.features.authentication.domain.use_cases

import com.debo.debo.features.authentication.domain.repostiory.AuthenticationRepository
import javax.inject.Inject

class GetUser @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke() = repository.getCurrentUser()
}