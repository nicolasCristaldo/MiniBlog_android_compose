package com.nicolascristaldo.miniblog.ui.screens.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseUser
import com.nicolascristaldo.miniblog.R
import com.nicolascristaldo.miniblog.domain.model.User
import com.nicolascristaldo.miniblog.ui.components.UserImage

@Composable
fun ProfileHeader(
    user: User?,
    authUser: FirebaseUser?,
    changeEditingState: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    )
    {
        UserImage(
            modifier = Modifier
                .size(dimensionResource(R.dimen.user_image_size_profile))
                .padding(dimensionResource(R.dimen.padding_large))
        )
        Text(
            text = user?.name ?: "",
            fontSize = dimensionResource(R.dimen.text_size_title_medium).value.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_large))
        )
        if (!user?.bio.isNullOrBlank()) {
            Text(
                text = user?.bio ?: "",
                fontSize = dimensionResource(R.dimen.text_size_medium).value.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.padding_large))
                    .fillMaxWidth()
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.profile_joined, user?.formatDate() ?: ""),
                fontSize = dimensionResource(R.dimen.text_size_medium).value.sp,
                modifier = Modifier.weight(1f)
            )
            if (user?.uid == authUser?.uid) {
                OutlinedButton(
                    onClick = { changeEditingState() },
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(text = stringResource(R.string.button_edit_profile))
                }
            }
        }
    }
}