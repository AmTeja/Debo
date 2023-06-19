package com.debo.debo.features.profile.data.repository

import com.debo.debo.features.profile.domain.model.Profile
import com.debo.debo.features.profile.domain.repository.ProfileRepository
import com.debo.debo.util.Constants
import com.debo.debo.util.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ProfileRepository {

    override fun getProfile(): Flow<Response<Profile>> = callbackFlow {
        Response.Loading
        val snapshotListener = firestore.collection(Constants.USERS_COLLECTION).document(
            auth.currentUser?.uid ?: ""
        ).addSnapshotListener { snapshot, error ->
            val response = if (error != null) {
                Response.Error(error.localizedMessage ?: "Error occurred")
            } else {
                if (snapshot?.exists() == true) {
                    val userInfo = snapshot.toObject(Profile::class.java)
                    Response.Success<Profile>(userInfo!!)
                } else
                    Response.Error("Profile not found")
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }
}