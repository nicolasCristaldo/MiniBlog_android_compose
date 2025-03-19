package com.nicolascristaldo.miniblog.ui.screens.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolascristaldo.miniblog.ui.screens.auth.components.AuthButton
import com.nicolascristaldo.miniblog.ui.components.AppTextField
import com.nicolascristaldo.miniblog.ui.theme.authTextFieldTextColor

@Composable
fun LogInScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState.isSuccess) { navigateToHome() }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = "Access Your Account",
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        AppTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = "email",
            validateInput = { true },
            textColor = authTextFieldTextColor,
            modifier = Modifier.padding(bottom = 8.dp, start = 32.dp, end = 32.dp)
        )

        AppTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = "password",
            validateInput = { true },
            visualTransformation = PasswordVisualTransformation(),
            textColor = authTextFieldTextColor,
            modifier = Modifier.padding(bottom = 16.dp, start = 32.dp, end = 32.dp)
        )

        AuthButton(
            onClick = { viewModel.login() },
            text = "Log in",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        uiState.error?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

