package com.debo.debo.features.authentication.data.repository

import com.debo.debo.features.authentication.domain.model.User
import com.debo.debo.features.authentication.domain.repostiory.AuthenticationRepository
import com.debo.debo.util.Constants
import com.debo.debo.util.Response
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthenticationRepository {
    var operationSuccessful: Boolean = false
    var user: User? = null

    override fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun getCurrentUser(): User? {
        return user
    }

    override fun login(email: String, password: String): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            emit(Response.Loading)
            suspendCoroutine<AuthResult> { continuation ->
                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener { it ->
                    operationSuccessful = true
                    firestore.collection(Constants.USERS_COLLECTION).document(it.user!!.uid)
                        .get().addOnSuccessListener { documentSnapshot ->
                            user = documentSnapshot.toObject(User::class.java)
                            continuation.resume(it)
                        }.addOnFailureListener {
                            continuation.resumeWithException(it)
                        }
                    continuation.resume(it)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
            }
            emit(Response.Success(operationSuccessful))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An Unknown Error has occurred!"))
        }
    }

    override fun logout(): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            auth.signOut()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An Unknown Error has occurred!"))
        }
    }

    override fun register(
        email: String,
        password: String,
        userName: String
    ): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            emit(Response.Loading)
            suspendCoroutine<QuerySnapshot> { continuation ->
                firestore.collection(Constants.USERS_COLLECTION).whereEqualTo("userName", userName)
                    .get().addOnSuccessListener {
                        if (it.isEmpty) {
                            operationSuccessful = true
                            continuation.resume(it)
                        } else {
                            continuation.resumeWithException(Exception("Username already exists"))
                        }
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }
            val authResult = suspendCoroutine<AuthResult> { continuation ->
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    operationSuccessful = true
                    continuation.resume(it)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
            }
            if (operationSuccessful) {
                val userId = authResult.user?.uid!!
                val obj = User(userName = userName, userId = userId, email = email)
                // remove password from user object

                firestore.collection(Constants.USERS_COLLECTION).document(userId).set(obj)
                    .addOnSuccessListener {
                        operationSuccessful = true
                        user = obj
                    }.addOnFailureListener {
                        operationSuccessful = false
                    }
                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Success(operationSuccessful))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An Unknown Error has occurred!"))
        }
    }


}