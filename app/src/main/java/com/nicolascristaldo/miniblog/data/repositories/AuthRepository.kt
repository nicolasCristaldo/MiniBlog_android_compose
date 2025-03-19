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

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {
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

    fun getAuthUserFlow(): Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser)
        }
        auth.addAuthStateListener(listener)
        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }

    fun signUpWithEmail(email: String, password: String): Flow<Result<Unit>> = flow {
        try {
            withContext(Dispatchers.IO) {
                auth.createUserWithEmailAndPassword(email, password).await()
                auth.currentUser?.sendEmailVerification()?.await()
                emit(Result.success(Unit))
            }
        }
        catch(e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun logInWithEmail(email: String, password: String): Flow<Result<Unit>> = flow {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val user = result.user ?: throw Exception("User not found")
        if (user.isEmailVerified) emit(Result.success(Unit))
        else throw Exception("Email not verified")
    }.catch {
        e -> emit(Result.failure(e))
    }.flowOn(Dispatchers.IO)

    fun resendVerificationEmail(): Flow<Result<Unit>> = flow {
        val user = auth.currentUser
        if (user != null && !user.isEmailVerified) {
            try {
                withContext(Dispatchers.IO) {
                    user.sendEmailVerification().await()
                    emit(Result.success(Unit))
                }
            }
            catch(e: Exception) {
                emit(Result.failure(e))
            }
        }
        else {
            emit(Result.failure(Exception("User not logged in or email already verified")))
        }
    }

    fun logOut() {
        auth.signOut()
    }
}