package com.debo.debo.features.posts.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.debo.debo.features.authentication.domain.model.User
import com.debo.debo.features.posts.domain.model.ImagePost
import com.debo.debo.features.posts.domain.model.Post
import com.debo.debo.features.posts.domain.model.TextPost
import com.debo.debo.features.posts.domain.model.VideoPost
import com.debo.debo.features.posts.domain.repository.PostRepository
import com.debo.debo.util.Constants
import com.debo.debo.util.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PostRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : PostRepository {

    override fun getPosts(): Flow<Response<List<Post>>> = callbackFlow {
        trySend(Response.Loading)
        try {
            val posts = mutableListOf<Post>()
            suspendCoroutine<QuerySnapshot> { continuation ->
                firestore.collection(Constants.POSTS_COLLECTION).get()
                    .addOnSuccessListener { querySnapshot ->
                        val postList = mutableListOf<Post>()
                        Log.d(TAG, "getPosts: ${querySnapshot.documents}")
                        for (document in querySnapshot.documents) {
                            val post: Post? = when (document.getString("postType")) {
                                "IMAGE" -> document.toObject(ImagePost::class.java)
                                "VIDEO" -> document.toObject(VideoPost::class.java)
                                "TEXT" -> document.toObject(TextPost::class.java)
                                else -> document.toObject(TextPost::class.java)
                            }
                            Log.d(TAG, "getPosts: $post")
                            if (post != null) {
                                //get post likes
                                firestore.collection(Constants.POSTS_COLLECTION).document(post.id)
                                    .collection(Constants.LIKES_COLLECTION).get()
                                    .addOnSuccessListener { likesQuerySnapshot ->
                                        val likes = mutableListOf<String>()
                                        for (likeDocument in likesQuerySnapshot.documents) {
                                            likes.add(likeDocument.id)
                                        }
                                        post.likes = likes
                                    }.addOnFailureListener { exception ->
                                        continuation.resumeWithException(exception)
                                    }


                                // Get user details

                                val userId = post.userId
                                firestore.collection(Constants.USERS_COLLECTION).document(userId)
                                    .get()
                                    .addOnSuccessListener { userDocument ->
                                        val user = userDocument.toObject(User::class.java)
                                        post.user = user ?: User()
                                        postList.add(post)
                                        if (postList.size == querySnapshot.documents.size) {
                                            Log.d(TAG, "getPosts: $postList")
                                            posts.addAll(postList)
                                            trySend(Response.Success(posts)).isSuccess
                                            continuation.resume(querySnapshot)
                                        }
                                    }.addOnFailureListener { exception ->
                                        continuation.resumeWithException(exception)
                                    }
                            }
                        }
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }
        } catch (e: Exception) {
            trySend(Response.Error(e.localizedMessage ?: "An Unknown Error has occurred!"))
        }
        awaitClose()
    }

    override fun createPost(post: Post): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun deletePost(postId: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun updatePost(post: Post): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun likePost(postId: String): Flow<Response<Boolean>> = callbackFlow {
        trySend(Response.Loading)
        try {
            val userId = auth.currentUser?.uid ?: ""
            firestore.collection(Constants.POSTS_COLLECTION).document(postId)
                .collection(Constants.LIKES_COLLECTION).document(userId)
                .set(hashMapOf("userId" to userId))
                .addOnSuccessListener {
                    trySend(Response.Success(true)).isSuccess
                }.addOnFailureListener {
                    trySend(Response.Error(it.localizedMessage ?: "An Unknown Error has occurred!"))
                }
        } catch (e: Exception) {
            trySend(Response.Error(e.localizedMessage ?: "An Unknown Error has occurred!"))
        }
        awaitClose()
    }

    override fun unlikePost(postId: String): Flow<Response<Boolean>> = callbackFlow {
        trySend(Response.Loading)
        try {
            val userId = auth.currentUser?.uid ?: ""
            firestore.collection(Constants.POSTS_COLLECTION).document(postId)
                .collection(Constants.LIKES_COLLECTION).document(userId)
                .delete()
                .addOnSuccessListener {
                    trySend(Response.Success(true)).isSuccess
                }.addOnFailureListener {
                    trySend(Response.Error(it.localizedMessage ?: "An Unknown Error has occurred!"))
                }
        } catch (e: Exception) {
            trySend(Response.Error(e.localizedMessage ?: "An Unknown Error has occurred!"))
        }
        awaitClose()
    }
}