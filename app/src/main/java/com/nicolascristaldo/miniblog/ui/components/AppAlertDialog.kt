package com.nicolascristaldo.miniblog.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nicolascristaldo.miniblog.R

@Composable
fun AppAlertDialog(
    title: Int,
    content: Int,
    confirmText: Int,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = {
            Text(
                text = stringResource(title),
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(R.dimen.text_size_title_small).value.sp
            )
        },
        text = { Text(text = stringResource(content)) },
        confirmButton = {
            Text(
                text = stringResource(confirmText),
                modifier = Modifier.clickable { onConfirm() }
            )
        },
        dismissButton = {
            Text(
                text = stringResource(R.string.dialog_cancel),
                modifier = Modifier.clickable { onCancel() }
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier
    )
}