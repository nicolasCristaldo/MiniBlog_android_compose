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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.nicolascristaldo.miniblog.R
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
            label = R.string.text_field_name_label,
            validateInput = { uiState.isValidName() },
            errorText = R.string.error_name_length,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
        )

        AppTextField(
            value = uiState.editingBio,
            onValueChange = { changeBio(it) },
            label = R.string.text_field_bio_label,
            validateInput = { uiState.isValidBio() },
            errorText = R.string.error_bio_validation,
            singleLine = false,
            maxLines = 4,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_large))
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { onCancel() }
            ) {
                Text(text = stringResource(R.string.button_cancel))
            }

            Button(
                onClick = { onSave() },
                enabled = uiState.isValidInputForm()
            ) {
                Text(text = stringResource(R.string.button_save))
            }
        }
    }
}