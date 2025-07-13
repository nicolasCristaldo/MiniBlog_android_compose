package com.nicolascristaldo.miniblog.ui.screens.auth.initial

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
                .size(dimensionResource(R.dimen.logo_size_initial))
                .padding(bottom = dimensionResource(R.dimen.padding_extra_large))
        )

        Text(
            text = stringResource(R.string.app_name),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(R.dimen.text_size_title_large).value.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        LogButton(
            text = R.string.button_signup,
            color = light_blue,
            onClick = { navigateToSignUp() },
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_large))
        )

        /*LogButton(
            text = R.string.button_continue_with_google,
            imageRes = R.drawable.google,
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_large))
        )*/

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.initial_already_have_account),
                color = Color.White,
                modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium))
            )
            Text(
                text = stringResource(R.string.button_login),
                color = light_blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navigateToLogIn() }
            )
        }

        Spacer(modifier = Modifier.weight(.5f))
    }
}
