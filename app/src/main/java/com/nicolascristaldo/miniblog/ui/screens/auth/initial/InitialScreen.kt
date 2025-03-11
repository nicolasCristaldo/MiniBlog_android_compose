package com.nicolascristaldo.miniblog.ui.screens.auth.initial

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolascristaldo.miniblog.R
import com.nicolascristaldo.miniblog.ui.components.LogoImage
import com.nicolascristaldo.miniblog.ui.screens.auth.initial.components.LogButton
import com.nicolascristaldo.miniblog.ui.theme.light_blue

@Composable
fun InitialScreen(
    navigateToLogIn: () -> Unit,
    navigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(.5f))
        LogoImage(
            modifier = Modifier
                .size(130.dp)
                .padding(bottom = 32.dp)
        )

        Text(
            text = "MiniBlog",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        LogButton(
            text = "Sign up",
            color = light_blue,
            onClick = { navigateToSignUp() },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LogButton(
            text = "Continue with Google",
            imageRes = R.drawable.google,
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Already have an account?",
                color = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Log in",
                color = light_blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navigateToLogIn() }
            )
        }

        Spacer(modifier = Modifier.weight(.5f))
    }
}
