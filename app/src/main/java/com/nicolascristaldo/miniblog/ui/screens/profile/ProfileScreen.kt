package com.nicolascristaldo.miniblog.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolascristaldo.miniblog.ui.components.UserImage
import com.nicolascristaldo.miniblog.ui.screens.post.PostListScreen

@Composable
fun ProfileScreen(
    uid: String,
    viewModel: ProfileViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        viewModel.listenUserChanges(uid = uid)
    }

    val user by viewModel.user.collectAsState()
    val authUser by viewModel.authUser.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        UserImage(
            modifier = Modifier
                .size(130.dp)
                .padding(16.dp)
        )
        Text(
            text = user?.name ?: "",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (!user?.bio.isNullOrBlank()) {
            Text(
                text = user?.bio ?: "",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Joined: " + viewModel.formatDate(user?.createdAt),
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            if(user?.uid == authUser?.uid) {
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Edit profile")
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        PostListScreen(
            currentUserId = authUser?.uid ?: "",
            uid = uid
        )
    }
}