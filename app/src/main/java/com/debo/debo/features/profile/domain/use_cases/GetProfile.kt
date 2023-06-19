package com.debo.debo.features.profile.domain.use_cases

import com.debo.debo.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfile @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke() = repository.getProfile()
}