package com.example.webbrowserapplication

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.compose.rememberNavController
import com.example.webbrowserapplication.ui.navigation.AppNavGraph
import com.example.webbrowserapplication.ui.notification.AppLifecycleTracker
import com.example.webbrowserapplication.ui.notification.NotificationHelper
import com.example.webbrowserapplication.ui.notification.NotificationPreferences
import com.example.webbrowserapplication.ui.theme.WebBrowserApplicationTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestNotificationPermission()
        checkWelcomeNotification()

        setContent {
            WebBrowserApplicationTheme {
                val navController = rememberNavController()

                AppNavGraph(navController = navController)
            }
        }
    }

    private fun requestNotificationPermission() {

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {

            notificationPermissionLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }

    private fun checkWelcomeNotification() {

        val prefs = NotificationPreferences(this)

        val today =
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).format(Date())

        val loginTime =
            prefs.getLoginTime()

        val diff =
            System.currentTimeMillis() -
                    loginTime

        val notShownToday =
            prefs.getLastShownDate() != today

        val notRecentlyLoggedIn =
            diff > 5 * 60 * 1000

        if (
            notShownToday &&
            notRecentlyLoggedIn &&
            !AppLifecycleTracker.isForeground
        ) {

            NotificationHelper
                .showWelcomeNotification(this)

            prefs.saveLastShownDate(today)
        }
    }


}

