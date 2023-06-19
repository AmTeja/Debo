package com.debo.debo.features.profile.presentation.profile

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debo.debo.features.profile.domain.model.Profile
import com.debo.debo.features.profile.domain.use_cases.ProfileUseCases
import com.debo.debo.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    private val _profileState = mutableStateOf<Response<Profile>>(Response.Initial)
    val profileState: State<Response<Profile>> = _profileState

    fun getProfile() {
        viewModelScope.launch {
            profileUseCases.getProfile().collect {
                _profileState.value = it
                Log.d(TAG, "getProfile: $it")
            }
        }
    }
}