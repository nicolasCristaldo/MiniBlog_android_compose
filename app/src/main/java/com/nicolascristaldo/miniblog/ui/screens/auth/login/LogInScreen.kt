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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolascristaldo.miniblog.R
import com.nicolascristaldo.miniblog.ui.components.AppTextField
import com.nicolascristaldo.miniblog.ui.screens.auth.components.AuthButton
import com.nicolascristaldo.miniblog.ui.theme.authTextFieldTextColor

@Composable
fun LogInScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState.isSuccess) {
            navigateToHome()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.login_title),
            fontSize = dimensionResource(R.dimen.text_size_title_large).value.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_extra_large))
        )

        AppTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = R.string.text_field_email_label,
            validateInput = { true },
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
            validateInput = { true },
            visualTransformation = PasswordVisualTransformation(),
            textColor = authTextFieldTextColor,
            modifier = Modifier.padding(
                bottom = dimensionResource(R.dimen.padding_large),
                start = dimensionResource(R.dimen.padding_extra_large),
                end = dimensionResource(R.dimen.padding_extra_large)
            )
        )

        AuthButton(
            onClick = { viewModel.login() },
            text = R.string.button_login,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
        )

        uiState.error?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                fontSize = dimensionResource(R.dimen.text_size_small).value.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

