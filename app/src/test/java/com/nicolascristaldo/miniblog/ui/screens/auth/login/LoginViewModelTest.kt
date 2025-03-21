package com.nicolascristaldo.miniblog.ui.screens.auth.login

import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @RelaxedMockK
    private lateinit var authRepository: AuthRepository

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        loginViewModel = LoginViewModel(authRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }


    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when login is successful then the state isSuccess is true, isLoading is false and error is null`() = runTest {
        //given
        coEvery { authRepository.logInWithEmail("", "") } returns flowOf(Result.success(Unit))
        //when
        loginViewModel.login()
        //then
        assertTrue(loginViewModel.uiState.value.isSuccess)
        assertFalse(loginViewModel.uiState.value.isLoading)
        assertNull(loginViewModel.uiState.value.error)
    }

    @Test
    fun `when login is not successful then the state isSuccess is false, isLoading is false and error is not null`() = runTest {
        //given
        coEvery { authRepository.logInWithEmail("", "") } returns flowOf(Result.failure(Exception("exception")))
        //when
        loginViewModel.login()
        //then
        assertFalse(loginViewModel.uiState.value.isSuccess)
        assertFalse(loginViewModel.uiState.value.isLoading)
        assertNotNull(loginViewModel.uiState.value.error)
    }
}