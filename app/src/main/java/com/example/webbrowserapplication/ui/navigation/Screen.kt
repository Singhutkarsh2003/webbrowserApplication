package com.example.webbrowserapplication.ui.navigation

sealed class Screen(
    val route : String
) {

    data object SignIn : Screen("sign_in")

    data object Home: Screen("home")

    data object WebView : Screen(
        "webview/{url}"
    ){
        fun createRoute(url : String): String {
         return  "webview/$url"
        }
    }

    data object History : Screen("history")
}