package com.nicolascristaldo.miniblog.ui.screens.auth.signup

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolascristaldo.miniblog.ui.screens.auth.components.AuthButton
import com.nicolascristaldo.miniblog.ui.components.AppTextField
import com.nicolascristaldo.miniblog.ui.theme.authTextFieldTextColor

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
        Text(
            text = "Create Your Account",
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        AppTextField(
            value = uiState.name,
            onValueChange = { viewModel.onNameChanged(it) },
            label = "Name",
            validateInput = { uiState.isValidName() },
            errorText = "Name must be at least 3 characters long.",
            textColor = authTextFieldTextColor,
            modifier = Modifier.padding(bottom = 8.dp, start = 32.dp, end = 32.dp)
        )

        AppTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = "Email",
            validateInput = { uiState.isValidEmail() },
            errorText = "Please enter a valid email address.",
            textColor = authTextFieldTextColor,
            modifier = Modifier.padding(bottom = 8.dp, start = 32.dp, end = 32.dp)
        )

        AppTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = "Password",
            validateInput = { uiState.isValidPassword() },
            visualTransformation = PasswordVisualTransformation(),
            errorText = "Password must be at least 6 characters long.",
            textColor = authTextFieldTextColor,
            modifier = Modifier.padding(bottom = 8.dp, start = 32.dp, end = 32.dp)
        )

        AppTextField(
            value = uiState.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChanged(it) },
            label = "Confirm password",
            validateInput = { uiState.arePasswordsEqual() },
            visualTransformation = PasswordVisualTransformation(),
            errorText = "Passwords do not match.",
            textColor = authTextFieldTextColor,
            modifier = Modifier.padding(bottom = 16.dp, start = 32.dp, end = 32.dp)
        )

        AuthButton(
            onClick = { viewModel.signUp() },
            enabled = uiState.validateInputForm() && !uiState.isLoading,
            text = "Sign up",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        uiState.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}