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
import org.junit.Assert.assertEquals
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

    @Test
    fun `onNameChanged updates name field`() = runTest {
        //given
        val name = "name"
        //when
        signUpViewModel.onNameChanged(name)
        //then
        assertEquals(name, signUpViewModel.uiState.value.name)
    }

    @Test
    fun `isValidName returns true when name is in valid range`() = runTest {
        //given
        signUpViewModel.onNameChanged("name")
        //when
        val isValid = signUpViewModel.uiState.value.isValidName()
        //then
        assertTrue(isValid)
    }

    @Test
    fun `isValidName returns false when name is not in valid range`() = runTest {
        //given
        signUpViewModel.onNameChanged("a")
        //when
        val isValid = signUpViewModel.uiState.value.isValidName()
        //then
        assertFalse(isValid)
    }

    @Test
    fun `onPasswordChanged updates password field`() = runTest {
        //given
        val password = "abc123"
        //when
        signUpViewModel.onPasswordChanged(password)
        //then
        assertEquals(password, signUpViewModel.uiState.value.password)
    }

    @Test
    fun `isValidPassword returns true when password is in valid range`() = runTest {
        //given
        signUpViewModel.onPasswordChanged("abc123")
        //when
        val isValid = signUpViewModel.uiState.value.isValidPassword()
        //then
        assertTrue(isValid)
    }

    @Test
    fun `isValidPassword returns false when password is not in valid range`() = runTest {
        //given
        signUpViewModel.onPasswordChanged("123")
        //when
        val isValid = signUpViewModel.uiState.value.isValidPassword()
        //then
        assertFalse(isValid)
    }

    @Test
    fun `onConfirmPasswordChanged updates confirm password field`() = runTest {
        //given
        val confirmPassword = "abc123"
        //when
        signUpViewModel.onConfirmPasswordChanged(confirmPassword)
        //then
        assertEquals(confirmPassword, signUpViewModel.uiState.value.confirmPassword)
    }

    @Test
    fun `arePasswordsEqual returns true when passwords are equal`() = runTest {
        //given
        signUpViewModel.onPasswordChanged("abc123")
        signUpViewModel.onConfirmPasswordChanged("abc123")
        //when
        val isValid = signUpViewModel.uiState.value.arePasswordsEqual()
        //then
        assertTrue(isValid)
    }

    @Test
    fun `arePasswordsEqual returns false when passwords are not equal`() = runTest {
        //given
        signUpViewModel.onPasswordChanged("abc123")
        signUpViewModel.onConfirmPasswordChanged("abc12345")
        //when
        val isValid = signUpViewModel.uiState.value.arePasswordsEqual()
        //then
        assertFalse(isValid)
    }

    @Test
    fun `onEmailChanged updates email field`() = runTest {
        //given
        val email = "user@email.com"
        //when
        signUpViewModel.onEmailChanged(email)
        //then
        assertEquals(email, signUpViewModel.uiState.value.email)
    }
}