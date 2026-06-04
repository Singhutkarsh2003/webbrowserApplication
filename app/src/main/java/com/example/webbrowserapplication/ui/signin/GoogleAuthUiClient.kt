package com.example.webbrowserapplication.ui.signin

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.webbrowserapplication.R
import com.example.webbrowserapplication.ui.signin.model.AuthResult
import com.example.webbrowserapplication.ui.signin.model.UserData
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val context : Context
){
    private val firebaseAuth : FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val credentialManger : CredentialManager by lazy { CredentialManager.create(context) }

    private val getGoogleIdOption : GetGoogleIdOption by lazy {
        GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .build()
    }

    private val getCredentialRequest : GetCredentialRequest by lazy {
        GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleIdOption)
            .build()
    }

    suspend fun signInWithGoogle(): AuthResult {

        try {
            val credential = credentialManger
                .getCredential(
                    context,
                    getCredentialRequest
                ).credential

            if (credential is CustomCredential
                && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ){

                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken

                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                firebaseAuth.signInWithCredential(firebaseCredential).await()

                googleIdTokenCredential.apply {
                    val userData = UserData(
                        userName = displayName,
                        userEmail = id,
                        profilePictureUrl = profilePictureUri
                    )
                    val authResult = AuthResult(userData)
                    return authResult
                }

            }else{
                return AuthResult(errorMessage = "Invalid credential ")
            }

        }catch (e: Exception){
            return AuthResult(errorMessage = e.toString())
        }

    }

    suspend fun signOut(){

        firebaseAuth.signOut()
        credentialManger.clearCredentialState(ClearCredentialStateRequest())

    }



}