package com.example.webbrowserapplication.ui.signin.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.webbrowserapplication.R

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit
){

    Button(

        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            disabledContainerColor = Color(0xFFEEEEEE),
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)

    ) {

            Icon(painter = painterResource(id = R.drawable.google),
                contentDescription = "Google Icons",
                tint = Color.Unspecified,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
               text =  "Continue with Google",
                color = Color(0xFF1F1F1F),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
    }
}