package com.nicolascristaldo.miniblog.di

import com.google.firebase.auth.FirebaseAuth
import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing authentication dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    /**
     * Provides a singleton instance of [FirebaseAuth].
     * @return The [FirebaseAuth] instance.
     */
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    /**
     * Provides an instance of [AuthRepository] with its required dependencies.
     * @param auth The [FirebaseAuth] instance to inject into the repository.
     * @return The [AuthRepository] instance.
     */
    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
        return AuthRepository(auth)
    }
}