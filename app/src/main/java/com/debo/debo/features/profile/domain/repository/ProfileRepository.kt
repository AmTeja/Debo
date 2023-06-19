package com.debo.debo.features.profile.domain.repository

import com.debo.debo.features.profile.domain.model.Profile
import com.debo.debo.util.Response
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(): Flow<Response<Profile>>
}