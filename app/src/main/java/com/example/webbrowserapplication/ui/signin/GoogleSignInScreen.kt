package com.example.webbrowserapplication.ui.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.webbrowserapplication.ui.signin.components.AppLogo
import com.example.webbrowserapplication.ui.signin.components.GoogleSignInButton
import com.example.webbrowserapplication.ui.signin.model.LoginStateType

@Composable
fun GoogleSignInScreen(
    viewModel: GoogleAuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit
) {

    val context = LocalContext.current

    val signInState by viewModel.signInState.collectAsState()

    LaunchedEffect(signInState.loginStateType) {

        if (
            signInState.loginStateType ==
            LoginStateType.LOGGED_IN
        ) {
            onLoginSuccess()
        }
    }

    LaunchedEffect(signInState.errorMessage) {

        signInState.errorMessage?.let {

            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()

            viewModel.resetError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 36.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        AppLogo()

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "WebToNative",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF2B8A8A)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Turn your web app into a native experience",
            fontSize = 15.sp,
            color = Color(0xFF9999BB),
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = Color(0xFF2E2E4A)
            )

            Text(
                text = " SIGN IN WITH ",
                color = Color(0xFF666680),
                fontSize = 11.sp
            )

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = Color(0xFF2E2E4A)
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        when (signInState.loginStateType) {

            LoginStateType.LOADING -> {

                CircularProgressIndicator()
            }

            LoginStateType.LOGGED_OUT -> {

                GoogleSignInButton(
                    onClick = {
                        viewModel.logIn()
                    }
                )
            }

            LoginStateType.LOGGED_IN -> {
                // Navigation handled in LaunchedEffect
            }

            LoginStateType.ERROR -> {

                GoogleSignInButton(
                    onClick = {
                        viewModel.logIn()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}