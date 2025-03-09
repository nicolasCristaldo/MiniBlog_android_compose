package com.nicolascristaldo.miniblog.ui.screens.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolascristaldo.miniblog.ui.screens.auth.components.AuthTextField

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToVerification: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState.isSuccess) { navigateToVerification() }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        AuthTextField(
            value = uiState.name,
            onValueChange = { viewModel.onNameChanged(it) },
            label = "Name",
            validateInput = { viewModel.isValidName(it) },
            errorText = "Name must be at least 3 characters long.",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        AuthTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = "Email",
            validateInput = { viewModel.isValidEmail(it) },
            errorText = "Please enter a valid email address.",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        AuthTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = "Password",
            validateInput = { viewModel.isValidPassword(it) },
            visualTransformation = PasswordVisualTransformation(),
            errorText = "Password must be at least 6 characters long.",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        AuthTextField(
            value = uiState.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChanged(it) },
            label = "Confirm password",
            validateInput = { viewModel.arePasswordsEqual(it, uiState.password) },
            visualTransformation = PasswordVisualTransformation(),
            errorText = "Passwords do not match.",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { viewModel.signUp() },
            enabled = viewModel.validateInputForm(),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(text = "Sign up")
        }

        uiState.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
        }
    }
}