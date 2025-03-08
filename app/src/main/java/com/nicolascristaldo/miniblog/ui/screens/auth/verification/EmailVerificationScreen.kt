package com.nicolascristaldo.miniblog.ui.screens.auth.verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nicolascristaldo.miniblog.ui.screens.auth.AuthViewModel

@Composable
fun EmailVerificationScreen(
    viewModel: AuthViewModel,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val verificationResult by viewModel.verificationResult.collectAsState()
    val resendTimer by viewModel.resendTimer.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startResetTimer()
        viewModel.startEmailVerificationCheck()
        viewModel.navigationEvent.collect { destination ->
            if (destination == "home") {
                navigateToHome()
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = "A verification email has been sent. Please confirm your account to continue."
        )
        CircularProgressIndicator()

        if(resendTimer > 0) {
            Text(
                text = "Resend verification email in $resendTimer seconds"
            )
        }
        Button(
            onClick = { viewModel.resendVerificationEmail() },
            enabled = resendTimer == 0
        ) {
            Text(text = "Resend verification email")
        }

        if(verificationResult?.isFailure == true) {
            Text(
                text = "Error: ${verificationResult?.exceptionOrNull()?.message}"
            )
        }
    }
}