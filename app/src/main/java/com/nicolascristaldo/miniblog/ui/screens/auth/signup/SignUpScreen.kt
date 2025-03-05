package com.nicolascristaldo.miniblog.ui.screens.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolascristaldo.miniblog.ui.screens.auth.components.AuthTextField

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uistate.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        AuthTextField(
            value = uiState.name,
            onValueChange = { viewModel.onNameChanged(it) },
            label = "Name",
            validateInput = { viewModel.isValidName(it) }
        )

        AuthTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = "Email",
            validateInput = { viewModel.isValidEmail(it) }
        )

        AuthTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = "Password",
            validateInput = { viewModel.isValidPassword(it) },
            visualTransformation = PasswordVisualTransformation()
        )

        AuthTextField(
            value = uiState.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChanged(it) },
            label = "Confirm password",
            validateInput = { viewModel.arePasswordsEqual(it, uiState.password) },
            visualTransformation = PasswordVisualTransformation()
        )

        uiState.error?.let {
            Text(
                text = it,
                color = Color.Red
            )
        }

        Button(
            onClick = { viewModel.signUp() },
            enabled = viewModel.validateInputForm()
        ) {
            Text(text = "Sign up")
        }
    }
}