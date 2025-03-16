package com.nicolascristaldo.miniblog.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun AppAlertDialog(
    title: String,
    content: String,
    confirmText: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        text = { Text(text = content) },
        confirmButton = {
            Text(
                text = confirmText,
                modifier = Modifier.clickable { onConfirm() }
            )
        },
        dismissButton = {
            Text(
                text = "Cancel",
                modifier = Modifier.clickable { onCancel() }
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier
    )
}