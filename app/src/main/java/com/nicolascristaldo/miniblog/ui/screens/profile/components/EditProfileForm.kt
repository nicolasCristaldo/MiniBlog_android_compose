package com.nicolascristaldo.miniblog.ui.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicolascristaldo.miniblog.ui.components.AppTextField
import com.nicolascristaldo.miniblog.ui.screens.profile.ProfileUiState

@Composable
fun EditProfileForm(
    uiState: ProfileUiState,
    changeName: (String) -> Unit,
    changeBio: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        changeName(uiState.user?.name ?: "")
        changeBio(uiState.user?.bio ?: "")
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        AppTextField(
            value = uiState.editingName,
            onValueChange = { changeName(it) },
            label = "Name",
            validateInput = { uiState.isValidName() },
            errorText = "Name must be between 3 and 20 characters",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        AppTextField(
            value = uiState.editingBio,
            onValueChange = { changeBio(it) },
            label = "Bio",
            validateInput = { uiState.isValidBio() },
            errorText = "Bio must be less than 100 characters",
            singleLine = false,
            maxLines = 4,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { onCancel() }
            ) {
                Text(text = "Cancel")
            }

            Button(
                onClick = { onSave() },
                enabled = uiState.isValidInputForm()
            ) {
                Text(text = "Save")
            }
        }
    }
}