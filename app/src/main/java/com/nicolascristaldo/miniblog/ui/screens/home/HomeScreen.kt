package com.nicolascristaldo.miniblog.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolascristaldo.miniblog.domain.model.User

@Composable
fun HomeScreen(
    user: User?,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    var value by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TextField(
            value = value,
            onValueChange = { value = it },
            placeholder = { Text(text = "Write a post") },
            maxLines = 5,
            trailingIcon = {
                if (value.isNotBlank()) {
                    Button(
                        onClick = {
                            //viewModel.createPost(value)
                            value = ""
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = "Send",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text = "latest posts",
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth()
        )


    }
}