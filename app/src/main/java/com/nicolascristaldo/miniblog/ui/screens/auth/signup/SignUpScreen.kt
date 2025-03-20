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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolascristaldo.miniblog.R
import com.nicolascristaldo.miniblog.ui.components.AppTextField
import com.nicolascristaldo.miniblog.ui.screens.auth.components.AuthButton
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
            text = stringResource(R.string.signup_title),
            fontSize = dimensionResource(R.dimen.text_size_title_large).value.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_extra_large))
        )

        AppTextField(
            value = uiState.name,
            onValueChange = { viewModel.onNameChanged(it) },
            label = R.string.text_field_name_label,
            validateInput = { uiState.isValidName() },
            errorText = R.string.error_name_length,
            textColor = authTextFieldTextColor,
            modifier = Modifier.padding(
                bottom = dimensionResource(R.dimen.padding_medium),
                start = dimensionResource(R.dimen.padding_extra_large),
                end = dimensionResource(R.dimen.padding_extra_large)
            )
        )

        AppTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = R.string.text_field_email_label,
            validateInput = { uiState.isValidEmail() },
            errorText = R.string.error_email_invalid,
            textColor = authTextFieldTextColor,
            modifier = Modifier.padding(
                bottom = dimensionResource(R.dimen.padding_medium),
                start = dimensionResource(R.dimen.padding_extra_large),
                end = dimensionResource(R.dimen.padding_extra_large)
            )
        )

        AppTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = R.string.text_field_password_label,
            validateInput = { uiState.isValidPassword() },
            visualTransformation = PasswordVisualTransformation(),
            errorText = R.string.error_password_length,
            textColor = authTextFieldTextColor,
            modifier = Modifier.padding(
                bottom = dimensionResource(R.dimen.padding_medium),
                start = dimensionResource(R.dimen.padding_extra_large),
                end = dimensionResource(R.dimen.padding_extra_large)
            )
        )

        AppTextField(
            value = uiState.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChanged(it) },
            label = R.string.text_field_confirm_password_label,
            validateInput = { uiState.arePasswordsEqual() },
            visualTransformation = PasswordVisualTransformation(),
            errorText = R.string.error_passwords_not_match,
            textColor = authTextFieldTextColor,
            modifier = Modifier.padding(
                bottom = dimensionResource(R.dimen.padding_large),
                start = dimensionResource(R.dimen.padding_extra_large),
                end = dimensionResource(R.dimen.padding_extra_large)
            )
        )

        AuthButton(
            onClick = { viewModel.signUp() },
            enabled = uiState.validateInputForm() && !uiState.isLoading,
            text = R.string.button_signup,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
        )

        uiState.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                fontSize = dimensionResource(R.dimen.text_size_small).value.sp,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}