package com.nicolascristaldo.miniblog.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolascristaldo.miniblog.R
import com.nicolascristaldo.miniblog.ui.screens.post.PostListScreen
import com.nicolascristaldo.miniblog.ui.screens.profile.components.EditProfileDialog
import com.nicolascristaldo.miniblog.ui.screens.profile.components.ProfileHeader

@Composable
fun ProfileScreen(
    uid: String,
    viewModel: ProfileViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.authUser != null) {
        LaunchedEffect(uid) {
            viewModel.listenUserChanges(uid = uid)
        }

        Box(modifier = modifier) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = if (uiState.isEditing) Modifier
                    .blur(dimensionResource(R.dimen.blur_radius))
                    .alpha(0.6f)
                else Modifier
            ) {
                ProfileHeader(
                    user = uiState.user,
                    authUser = uiState.authUser,
                    changeEditingState = { viewModel.changeEditingState(true) },
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_large))
                )

                HorizontalDivider(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium)))

                PostListScreen(
                    currentUserId = uiState.authUser?.uid ?: "",
                    uid = uid,
                    modifier = Modifier.fillMaxSize()
                )
            }

            if (uiState.isEditing) {
                EditProfileDialog(
                    uiState = uiState,
                    changeName = viewModel::changeEditingName,
                    changeBio = viewModel::changeEditingBio,
                    changeEditingState = viewModel::changeEditingState,
                    updateUser = viewModel::updateUser
                )
            }
        }
    }
}