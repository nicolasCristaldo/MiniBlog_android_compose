package com.nicolascristaldo.miniblog.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nicolascristaldo.miniblog.ui.screens.profile.ProfileUiState

@Composable
fun EditProfileDialog(
    uiState: ProfileUiState,
    changeName: (String) -> Unit,
    changeBio: (String) -> Unit,
    changeEditingState: (Boolean) -> Unit,
    updateUser: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = { changeEditingState(false) }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            EditProfileForm(
                uiState = uiState,
                changeName = changeName,
                changeBio = changeBio,
                onSave = {
                    updateUser()
                    changeEditingState(false)
                },
                onCancel = { changeEditingState(false) }
            )
        }
    }
}