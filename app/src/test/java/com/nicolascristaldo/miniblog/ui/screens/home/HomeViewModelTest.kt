package com.nicolascristaldo.miniblog.ui.screens.home

import com.google.firebase.auth.FirebaseUser
import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import com.nicolascristaldo.miniblog.domain.model.User
import com.nicolascristaldo.miniblog.domain.usecases.GetAuthUserUseCase
import com.nicolascristaldo.miniblog.domain.usecases.GetUserUseCase
import com.nicolascristaldo.miniblog.domain.usecases.LogOutUseCase
import com.nicolascristaldo.miniblog.domain.usecases.PublishPostUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @RelaxedMockK
    private lateinit var authRepository: AuthRepository
    @RelaxedMockK
    private lateinit var getAuthUserUseCase: GetAuthUserUseCase
    @RelaxedMockK
    private lateinit var logOutUseCase: LogOutUseCase
    @RelaxedMockK
    private lateinit var getUserUseCase: GetUserUseCase
    @RelaxedMockK
    private lateinit var publishPostUseCase: PublishPostUseCase

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        homeViewModel = HomeViewModel(
            authRepository = authRepository,
            getAuthUserUseCase = getAuthUserUseCase,
            logOutUseCase = logOutUseCase,
            getUserUseCase = getUserUseCase,
            publishPostUseCase = publishPostUseCase
        )
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `isValidPost returns true when post content is valid`() = runTest {
        //given
        val postContent = "This is a valid post content."
        homeViewModel.changePostContent(postContent)
        //when
        val isValid = homeViewModel.uiState.value.isValidPost()
        //then
        assertTrue(isValid)
    }

    @Test
    fun `isValidPost returns false when post content is not valid`() = runTest {
        //given
        val postContent = "This is a invalid post content because " +
                "it is too long and exceeds the limit of 200 characters in length. " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
        homeViewModel.changePostContent(postContent)
        //when
        val isValid = homeViewModel.uiState.value.isValidPost()
        //then
        assertFalse(isValid)
    }

    @Test
    fun `changePostContent updates postContent field`() = runTest {
        //given
        val postContent = "content"
        //when
        homeViewModel.changePostContent(postContent)
        //then
        assertEquals(postContent, homeViewModel.uiState.value.postContent)
    }

    @Test
    fun `changeLogOutDialogState updates showLogOutDialog state`() = runTest {
        //given
        val show = true
        //when
        homeViewModel.changeLogOutDialogState(show)
        //then
        assertTrue(homeViewModel.uiState.value.showLogOutDialog)
    }

    @Test
    fun `logOut resets uiState`() = runTest {
        //given
        val dialogState = true
        val postContent = "content"
        //when
        homeViewModel.changeLogOutDialogState(dialogState)
        homeViewModel.changePostContent(postContent)
        homeViewModel.logOut()
        //then
        assertEquals(HomeUiState(), homeViewModel.uiState.value)
    }

    @Test
    fun `loadUser updates user field`() = runTest {
        // Given
        val firebaseUser = mockk<FirebaseUser> {
            every { uid } returns "user123"
            every { email } returns "user@email.com"
        }
        val user = User(uid = "user123", name = "Test User", email = "user@email.com")
        coEvery { getAuthUserUseCase() } returns firebaseUser
        coEvery { getUserUseCase("user123") } returns user
        // When
        homeViewModel.loadUser()
        // Then
        assertEquals(user, homeViewModel.uiState.value.user)
    }
}