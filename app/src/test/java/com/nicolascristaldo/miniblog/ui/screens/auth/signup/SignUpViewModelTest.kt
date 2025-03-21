package com.nicolascristaldo.miniblog.ui.screens.auth.signup

import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import com.nicolascristaldo.miniblog.domain.usecases.SaveUserUseCase
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
class SignUpViewModelTest {
    @RelaxedMockK
    private lateinit var authRepository: AuthRepository

    @RelaxedMockK
    private lateinit var saveUserUseCase: SaveUserUseCase

    private lateinit var signUpViewModel: SignUpViewModel

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        signUpViewModel = SignUpViewModel(authRepository, saveUserUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when signup is successful then the state isSuccess is true, isLoading is false and error is null`() = runTest {
        //given
        coEvery { authRepository.signUpWithEmail("", "") } returns flowOf(Result.success(Unit))
        coEvery { saveUserUseCase(any()) } returns Unit
        //when
        signUpViewModel.signUp()
        //then
        assertTrue(signUpViewModel.uiState.value.isSuccess)
        assertFalse(signUpViewModel.uiState.value.isLoading)
        assertNull(signUpViewModel.uiState.value.error)
    }

    @Test
    fun `when signup is not successful then the state isSuccess is false, isLoading is false and error is not null`() = runTest {
        //given
        coEvery { authRepository.signUpWithEmail("", "") } returns flowOf(Result.failure(Exception("exception")))
        coEvery { saveUserUseCase(any()) } returns Unit
        //when
        signUpViewModel.signUp()
        //then
        assertFalse(signUpViewModel.uiState.value.isSuccess)
        assertFalse(signUpViewModel.uiState.value.isLoading)
        assertNotNull(signUpViewModel.uiState.value.error)
    }
}