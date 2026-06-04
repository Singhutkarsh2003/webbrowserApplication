package com.example.webbrowserapplication.ui.home

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.webbrowserapplication.ui.navigation.Screen
import com.example.webbrowserapplication.ui.signin.GoogleAuthViewModel
import com.example.webbrowserapplication.ui.signin.model.LoginStateType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController : NavController,
    authViewModel: GoogleAuthViewModel,
    homeViewModel: HomeViewModel = viewModel()
){

    val signInState by authViewModel.signInState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("WebToNative") },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.History.route)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.History, contentDescription = "History")
                    }
                    LaunchedEffect(signInState.loginStateType) {

                        if (
                            signInState.loginStateType ==
                            LoginStateType.LOGGED_OUT
                        ) {

                            navController.navigate(
                                Screen.SignIn.route
                            ) {
                                popUpTo(0)
                            }
                        }
                    }
                    IconButton(
                        onClick = {
                            authViewModel.logOut()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout")
                    }
                }
            )
        }
    ) {paddingValues ->
        Column(modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {

            ImageCarousel()
            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(
                value = homeViewModel.url,
                onValueChange = {
                    homeViewModel.updateUrl(it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Enter Url")},
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (homeViewModel.url.isNotBlank()){
                        val finalUrl = if(homeViewModel.url.startsWith("http")) homeViewModel.url
                        else "https://${homeViewModel.url}"

                        navController.navigate(
                            Screen.WebView.createRoute(
                                Uri.encode(finalUrl)
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Open App")
            }

        }
    }

}