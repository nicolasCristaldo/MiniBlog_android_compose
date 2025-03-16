package com.nicolascristaldo.miniblog.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseUser
import com.nicolascristaldo.miniblog.domain.model.User
import com.nicolascristaldo.miniblog.ui.screens.post.PostListScreen
import com.nicolascristaldo.miniblog.ui.screens.profile.components.EditProfileDialog
import com.nicolascristaldo.miniblog.ui.screens.profile.components.ProfileHeader

//@Composable
//fun ProfileScreen(
//    uid: String,
//    viewModel: ProfileViewModel = hiltViewModel(),
//    modifier: Modifier = Modifier
//) {
//    val user by viewModel.user.collectAsState()
//    val authUser by viewModel.authUser.collectAsState()
//    val isEditing by viewModel.isEditing.collectAsState()
//
//    if (authUser != null) {
//        // Solo se ejecuta una vez, asumiendo que uid no cambia
//        LaunchedEffect(Unit) {
//            viewModel.listenUserChanges(uid = uid)
//        }
//
//        Box(modifier = modifier) {
//            if (user != null) {
//                ProfileContent(
//                    user = user!!, // Forzamos no-null porque ya comprobamos
//                    authUser = authUser!!,
//                    isEditing = isEditing,
//                    onEditClick = { viewModel.changeEditingState(true) },
//                    uid = uid,
//                    formatDate = { timestamp -> viewModel.formatDate(timestamp) }
//                )
//            } else {
//                // Indicador de carga mientras user es null
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//            }
//
//            if (isEditing && user != null) {
//                EditProfileDialog(
//                    user = user!!,
//                    changeEditingState = { viewModel.changeEditingState(false) },
//                    updateUser = { name, bio -> viewModel.updateUser(name, bio) }
//                )
//            }
//        }
//    } else {
//        Text(
//            text = "Usuario no autenticado",
//            modifier = Modifier.fillMaxSize(),
//            textAlign = TextAlign.Center
//        )
//    }
//}
//
//@Composable
//private fun ProfileContent(
//    user: User,
//    authUser: FirebaseUser,
//    isEditing: Boolean,
//    onEditClick: () -> Unit,
//    uid: String,
//    formatDate: (Long?) -> String,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = if (isEditing) Modifier
//            .blur(8.dp)
//            .alpha(0.6f)
//        else modifier
//    ) {
//        ProfileHeader(
//            user = user,
//            authUser = authUser,
//            formatDate = { timestamp -> formatDate(timestamp) },
//            changeEditingState = onEditClick,
//            modifier = Modifier.padding(horizontal = 16.dp)
//        )
//
//        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
//
//        PostListScreen(
//            currentUserId = authUser.uid,
//            uid = uid
//        )
//    }
//}
@Composable
fun ProfileScreen(
    uid: String,
    viewModel: ProfileViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val user by viewModel.user.collectAsState()
    val authUser by viewModel.authUser.collectAsState()
    val isEditing by viewModel.isEditing.collectAsState()

    if (authUser != null) {
        LaunchedEffect(uid) {
            viewModel.listenUserChanges(uid = uid)
        }

        Box(modifier = modifier) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = if (isEditing) Modifier
                    .blur(8.dp)
                    .alpha(0.6f)
                else Modifier
            ) {
                ProfileHeader(
                    user = user,
                    authUser = authUser,
                    formatDate = { timestamp -> viewModel.formatDate(timestamp) },
                    changeEditingState = { viewModel.changeEditingState(true) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                HorizontalDivider(modifier = Modifier.padding(top = 8.dp))

                PostListScreen(
                    currentUserId = authUser?.uid ?: "",
                    uid = uid
                )
            }

            if (isEditing) {
                EditProfileDialog(
                    user = user,
                    changeEditingState = { viewModel.changeEditingState(false) },
                    updateUser = { name, bio -> viewModel.updateUser(name, bio) }
                )
            }
        }
    }
}