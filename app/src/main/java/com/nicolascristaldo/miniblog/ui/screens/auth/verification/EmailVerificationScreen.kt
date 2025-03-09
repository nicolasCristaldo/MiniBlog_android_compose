package com.nicolascristaldo.miniblog.ui.screens.auth.verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Spacer(modifier = Modifier.weight(.5f))
        Text(
            text = "A verification email has been sent. Please confirm your account to continue.",
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))
        CircularProgressIndicator()
        Spacer(modifier = Modifier.weight(1f))

        if(resendTimer > 0) {
            Text(
                text = "Resend verification email in $resendTimer seconds",
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = { viewModel.resendVerificationEmail() },
            enabled = resendTimer == 0,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Resend verification email")
        }

        if(verificationResult?.isFailure == true) {
            Text(
                text = "Error: ${verificationResult?.exceptionOrNull()?.message}",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.weight(.5f))
    }
}