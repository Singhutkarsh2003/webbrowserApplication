package com.example.webbrowserapplication.ui.signin.model

data class SignInState(
    val loginStateType: LoginStateType = LoginStateType.LOGGED_OUT,
    val userData: UserData? = null,
    val errorMessage : String ?= null
)

enum class LoginStateType{
    LOGGED_IN,
    LOGGED_OUT,

    LOADING,
    ERROR
}
