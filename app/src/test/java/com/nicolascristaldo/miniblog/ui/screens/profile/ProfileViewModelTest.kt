package com.nicolascristaldo.miniblog.ui.screens.profile

import com.nicolascristaldo.miniblog.data.repositories.AuthRepository
import com.nicolascristaldo.miniblog.data.repositories.UsersRepository
import com.nicolascristaldo.miniblog.domain.usecases.GetAuthUserUseCase
import com.nicolascristaldo.miniblog.domain.usecases.UpdateUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
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
class ProfileViewModelTest {
    @RelaxedMockK
    private lateinit var authRepository: AuthRepository
    @RelaxedMockK
    private lateinit var usersRepository: UsersRepository
    @RelaxedMockK
    private lateinit var getAuthUserUseCase: GetAuthUserUseCase
    @RelaxedMockK
    private lateinit var updateUserUseCase: UpdateUserUseCase

    private lateinit var profileViewModel: ProfileViewModel

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        profileViewModel = ProfileViewModel(
            authRepository = authRepository,
            usersRepository = usersRepository,
            getAuthUserUseCase = getAuthUserUseCase,
            updateUserUseCase = updateUserUseCase
        )
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `changeEditingState updates isEditing state`() = runTest {
        //given
        val isEditing = true
        //when
        profileViewModel.changeEditingState(isEditing)
        //then
        assertTrue(profileViewModel.uiState.value.isEditing)
    }

    @Test
    fun `changeEditingName updates editingName state`() = runTest {
        //given
        val editingName = "New Name"
        //when
        profileViewModel.changeEditingName(editingName)
        //then
        assertEquals(editingName, profileViewModel.uiState.value.editingName)
    }

    @Test
    fun `isValidName returns true when name is valid`() = runTest {
        //given
        val name = "Valid name"
        profileViewModel.changeEditingName(name)
        //when
        val isValid = profileViewModel.uiState.value.isValidName()
        //then
        assertTrue(isValid)
    }

    @Test
    fun `isValidName returns false when name is not valid`() = runTest {
        //given
        val name = "invalid name because it is too long and exceeds the limit of 20 characters in length."
        profileViewModel.changeEditingName(name)
        //when
        val isValid = profileViewModel.uiState.value.isValidName()
        //then
        assertFalse(isValid)
    }

    @Test
    fun `changeEditingBio updates editingBio state`() = runTest {
        //given
        val editingBio = "New Bio"
        //when
        profileViewModel.changeEditingBio(editingBio)
        //then
        assertEquals(editingBio, profileViewModel.uiState.value.editingBio)
    }

    @Test
    fun `isValidBio returns true when bio is valid`() = runTest {
        //given
        val bio = "Valid bio"
        profileViewModel.changeEditingBio(bio)
        //when
        val isValid = profileViewModel.uiState.value.isValidBio()
        //then
        assertTrue(isValid)
    }

    @Test
    fun `isValidBio returns false when bio is not valid`() = runTest {
        //given
        val bio = "Invalid bio because it is too long and exceeds " +
                "the limit of 100 characters in length." +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
        profileViewModel.changeEditingBio(bio)
        //when
        val isValid = profileViewModel.uiState.value.isValidBio()
        //then
        assertFalse(isValid)
    }

    @Test
    fun `isValidInputForm returns true when name and bio are valid`() = runTest {
        //given
        val name = "Valid name"
        val bio = "Valid bio"
        profileViewModel.changeEditingName(name)
        profileViewModel.changeEditingBio(bio)
        //when
        val isValid = profileViewModel.uiState.value.isValidInputForm()
        //then
        assertTrue(isValid)
    }

    @Test
    fun `isValidInputForm returns false when name is not valid`() = runTest {
        //given
        val name = "Invalid name because it is too long and exceeds the limit of 20 characters in length."
        val bio = "Valid bio"
        profileViewModel.changeEditingName(name)
        profileViewModel.changeEditingBio(bio)
        //when
        val isValid = profileViewModel.uiState.value.isValidInputForm()
        //then
        assertFalse(isValid)
    }

    @Test
    fun `isValidInputForm returns false when bio is not valid`() = runTest {
        //given
        val name = "Valid name"
        val bio = "Invalid bio because it is too long and exceeds " +
                "the limit of 100 characters in length." +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
        profileViewModel.changeEditingName(name)
        profileViewModel.changeEditingBio(bio)
        //when
        val isValid = profileViewModel.uiState.value.isValidInputForm()
        //then
        assertFalse(isValid)
    }

    @Test
    fun `updateUser updates isEditing state to false`() = runTest {
        //given
        val isEditing = true
        profileViewModel.changeEditingState(isEditing)
        //when
        profileViewModel.updateUser()
        //then
        assertFalse(profileViewModel.uiState.value.isEditing)
    }
}