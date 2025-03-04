package com.nicolascristaldo.miniblog.ui.screens.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolascristaldo.miniblog.R
import com.nicolascristaldo.miniblog.ui.theme.black
import com.nicolascristaldo.miniblog.ui.theme.dark_blue
import com.nicolascristaldo.miniblog.ui.theme.light_blue

@Composable
fun InitialScreen(
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(
                verticalGradient(
                    colors = listOf(black, dark_blue),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    ) {
        Spacer(modifier = Modifier.weight(.5f))
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = null,
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
            onClick = { /*TODO*/ },
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
                modifier = Modifier.clickable { /*TODO*/ }
            )
        }

        Spacer(modifier = Modifier.weight(.5f))
    }
}

@Composable
fun LogButton(
    text: String,
    imageRes: Int? = null,
    color: Color = Color.White,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 32.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = color)
            .clickable { onClick() }
    ) {
        if (imageRes != null) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(24.dp)
            )
        }
        Text(
            text = text,
            color = dark_blue,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
