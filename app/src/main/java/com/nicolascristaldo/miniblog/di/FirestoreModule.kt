package com.nicolascristaldo.miniblog.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module to providing Firestore dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {

    /**
     * Provides a singleton instance of [FirebaseFirestore].
     * @return The [FirebaseFirestore] instance.
     */
    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}