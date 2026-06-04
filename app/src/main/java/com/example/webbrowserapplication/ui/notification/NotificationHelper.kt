package com.example.webbrowserapplication.ui.notification


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.webbrowserapplication.R

object NotificationHelper {

    private const val CHANNEL_ID = "welcome_channel"

    fun showWelcomeNotification(
        context: Context
    ) {

        val manager =
            context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                "Welcome Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            manager.createNotificationChannel(channel)
        }

        val notification =
            NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Welcome Back")
                .setContentText("Thanks for opening the app")
                .setAutoCancel(true)
                .build()

        manager.notify(
            1001,
            notification
        )
    }
}