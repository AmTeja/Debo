package com.debo.debo.di

import com.debo.debo.features.authentication.data.repository.AuthenticationRepositoryImpl
import com.debo.debo.features.authentication.domain.repostiory.AuthenticationRepository
import com.debo.debo.features.authentication.domain.use_cases.AuthenticationUseCases
import com.debo.debo.features.authentication.domain.use_cases.FirebaseAuthState
import com.debo.debo.features.authentication.domain.use_cases.GetUser
import com.debo.debo.features.authentication.domain.use_cases.IsUserAuthenticated
import com.debo.debo.features.authentication.domain.use_cases.Login
import com.debo.debo.features.authentication.domain.use_cases.Logout
import com.debo.debo.features.authentication.domain.use_cases.Register
import com.debo.debo.features.posts.data.repository.PostRepositoryImpl
import com.debo.debo.features.posts.domain.repository.PostRepository
import com.debo.debo.features.posts.domain.use_cases.GetPosts
import com.debo.debo.features.posts.domain.use_cases.LikePost
import com.debo.debo.features.posts.domain.use_cases.PostUseCases
import com.debo.debo.features.posts.domain.use_cases.UnlikePost
import com.debo.debo.features.profile.data.repository.ProfileRepositoryImpl
import com.debo.debo.features.profile.domain.repository.ProfileRepository
import com.debo.debo.features.profile.domain.use_cases.GetProfile
import com.debo.debo.features.profile.domain.use_cases.ProfileUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuthentication(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Singleton
    @Provides
    fun provideAuthenticationRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(auth, firestore)
    }

    @Singleton
    @Provides
    fun provideAuthenticationUseCases(repository: AuthenticationRepository) =
        AuthenticationUseCases(
            isUserAuthenticated = IsUserAuthenticated(repository),
            login = Login(repository),
            register = Register(repository),
            logout = Logout(repository),
            firebaseAuthState = FirebaseAuthState(repository),
            getUser = GetUser(repository)
        )

    @Singleton
    @Provides
    fun provideProfileRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): ProfileRepository {
        return ProfileRepositoryImpl(firestore, auth)
    }

    @Singleton
    @Provides
    fun provideProfileUseCases(repository: ProfileRepository) =
        ProfileUseCases(
            getProfile = GetProfile(repository),
        )

    @Singleton
    @Provides
    fun providePostRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): PostRepository {
        return PostRepositoryImpl(firestore, auth)
    }

    @Singleton
    @Provides
    fun providePostUseCases(repository: PostRepository) =
        PostUseCases(
            getPosts = GetPosts(repository),
            likePost = LikePost(repository),
            unlikePost = UnlikePost(repository)
        )
}