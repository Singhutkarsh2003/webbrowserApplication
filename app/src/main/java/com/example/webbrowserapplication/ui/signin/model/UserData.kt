package com.example.webbrowserapplication.ui.signin.model

import android.net.Uri

data class UserData(
    val userName : String? = null,
    val userEmail : String? = null,
    val profilePictureUrl : Uri? = null
)