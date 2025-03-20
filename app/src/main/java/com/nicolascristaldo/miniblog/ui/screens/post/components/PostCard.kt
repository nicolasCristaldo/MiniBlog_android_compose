package com.nicolascristaldo.miniblog.ui.screens.post.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.nicolascristaldo.miniblog.R
import com.nicolascristaldo.miniblog.domain.model.PostWithUser
import com.nicolascristaldo.miniblog.ui.components.UserImage

@Composable
fun PostCard(
    currentUserId: String,
    postWhitUser: PostWithUser,
    onDeletePost: () -> Unit,
    navigateToUserProfile: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        UserImage(
            modifier = Modifier
                .size(dimensionResource(R.dimen.user_image_size_small))
                .clickable { navigateToUserProfile(postWhitUser.user.uid) }
        )
        Column(
            modifier = Modifier
                .padding(start = dimensionResource(R.dimen.padding_medium))
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .height(dimensionResource(R.dimen.post_card_header_height))
                    .fillMaxWidth()
            ) {
                Text(
                    text = postWhitUser.user.name,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(R.dimen.text_size_title_small).value.sp,
                    modifier = Modifier
                        .clickable { navigateToUserProfile(postWhitUser.user.uid) }
                )
                Spacer(modifier = Modifier.weight(1f))
                if(currentUserId == postWhitUser.post.userId){
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_icon_content_description),
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.icon_size_small))
                            .clickable { onDeletePost() }
                    )
                }
            }
            Text(
                text = postWhitUser.post.content
            )
            Text(
                text = postWhitUser.post.formatDate(),
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = dimensionResource(R.dimen.text_size_small).value.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}