package com.example.webbrowserapplication.ui.signin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.webbrowserapplication.ui.notification.NotificationPreferences
import com.example.webbrowserapplication.ui.signin.model.SignInState
import com.example.webbrowserapplication.ui.signin.model.LoginStateType
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GoogleAuthViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _signInState = MutableStateFlow(SignInState())
    val signInState: StateFlow<SignInState> =
        _signInState.asStateFlow()

    private val authManager by lazy {
        GoogleAuthUiClient(application.applicationContext)
    }

    init {
        checkUserSession()
    }

    private fun checkUserSession() {

        if (FirebaseAuth.getInstance().currentUser != null) {

            _signInState.value = SignInState(
                loginStateType = LoginStateType.LOGGED_IN
            )
        } else {

            _signInState.value = SignInState(
                loginStateType = LoginStateType.LOGGED_OUT
            )
        }
    }

    fun logIn() {

        viewModelScope.launch {

            _signInState.value = SignInState(
                loginStateType = LoginStateType.LOADING
            )

            val result = authManager.signInWithGoogle()

            result.errorMessage?.let { error ->

                _signInState.value = SignInState(
                    loginStateType = LoginStateType.ERROR,
                    errorMessage = error
                )

                return@launch
            }

            _signInState.value = SignInState(
                loginStateType = LoginStateType.LOGGED_IN,
                userData = result.data
            )

            NotificationPreferences(
                application
            ).saveLoginTime(
                System.currentTimeMillis()
            )
        }
    }

    fun logOut() {

        viewModelScope.launch {

            authManager.signOut()

            _signInState.value = SignInState(
                loginStateType = LoginStateType.LOGGED_OUT
            )
        }
    }

    fun resetError() {

        if (_signInState.value.loginStateType == LoginStateType.ERROR) {

            _signInState.value = SignInState(
                loginStateType = LoginStateType.LOGGED_OUT
            )
        }
    }
}