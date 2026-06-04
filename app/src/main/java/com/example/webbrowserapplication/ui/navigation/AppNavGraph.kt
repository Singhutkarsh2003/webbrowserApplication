package com.example.webbrowserapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.webbrowserapplication.ui.history.HistoryScreen
import com.example.webbrowserapplication.ui.home.HomeScreen
import com.example.webbrowserapplication.ui.signin.GoogleAuthViewModel
import com.example.webbrowserapplication.ui.signin.GoogleSignInScreen
import com.example.webbrowserapplication.ui.webview.WebViewScreen
import java.net.URLDecoder

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    val authViewModel: GoogleAuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.SignIn.route,
        modifier = modifier
    ){
        composable(
            route = Screen.SignIn.route
        ) {
            GoogleSignInScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(
                        Screen.Home.route
                    ){
                        popUpTo(
                            Screen.SignIn.route
                        ){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable (route = Screen.Home.route){
            HomeScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable (route = Screen.WebView.route,
            arguments = listOf(
                navArgument("url"){
                    type = NavType.StringType
                }
            )
        ){backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url")?:""

            val url = URLDecoder.decode(
                encodedUrl,
                "UTF-8"
            )
            WebViewScreen(
                url = url,
                navController = navController
            )

        }

        composable (route = Screen.History.route){
            HistoryScreen(
                navController = navController
            )
        }

    }

}