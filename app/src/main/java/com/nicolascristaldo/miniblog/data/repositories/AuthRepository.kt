package com.nicolascristaldo.miniblog.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository for managing authentication operations.
 */
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {
    /**
     * Retrieves the currently authenticated user.
     * @return The current [FirebaseUser] if authenticated, or null if no user is signed in.
     */
    suspend fun getAuthUser(): FirebaseUser? = withContext(Dispatchers.IO) {
        return@withContext try {
            if (auth.currentUser != null) {
                auth.currentUser!!.reload().await()
                auth.currentUser
            } else null
        } catch (e: Exception) {
            logOut()
            null
        }
    }

    /**
     * Provides a flow emitting the current authenticated user whenever the authentication state changes.
     * @return A [Flow] emitting the current [FirebaseUser] or null if not authenticated.
     */
    fun getAuthUserFlow(): Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser)
        }
        auth.addAuthStateListener(listener)
        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }

    /**
     * Signs up a new user with the given email and password, and sends a verification email.
     * @param email The email address for the new user.
     * @param password The password for the new user.
     * @return A [Flow] emitting [Result.success] on successful signup, or [Result.failure] on error.
     */
    fun signUpWithEmail(email: String, password: String): Flow<Result<Unit>> = flow {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            auth.currentUser?.sendEmailVerification()?.await()
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Logs in a user with the provided email and password, verifying their email is confirmed.
     * @param email The user's email address.
     * @param password The user's password.
     * @return A [Flow] emitting [Result.success] if login succeeds and email is verified, or [Result.failure] otherwise.
     */
    fun logInWithEmail(email: String, password: String): Flow<Result<Unit>> = flow {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val user = result.user ?: throw Exception("User not found")
        if (user.isEmailVerified) emit(Result.success(Unit))
        else throw Exception("Email not verified")
    }.catch { e ->
        emit(Result.failure(e))
    }.flowOn(Dispatchers.IO)

    /**
     * Resends a verification email to the currently authenticated user.
     * @return A [Flow] emitting [Result.success] if the email is sent, or [Result.failure] if an error occurs.
     */
    fun resendVerificationEmail(): Flow<Result<Unit>> = flow {
        val user = auth.currentUser
        if (user != null && !user.isEmailVerified) {
            try {
                withContext(Dispatchers.IO) {
                    user.sendEmailVerification().await()
                    emit(Result.success(Unit))
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        } else {
            emit(Result.failure(Exception("User not logged in or email already verified")))
        }
    }

    /**
     * Logs out the currently authenticated user.
     */
    fun logOut() {
        auth.signOut()
    }
}