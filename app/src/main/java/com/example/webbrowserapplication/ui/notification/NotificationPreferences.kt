package com.example.webbrowserapplication.ui.notification


import android.content.Context

class NotificationPreferences(
    context: Context
) {

    private val prefs =
        context.getSharedPreferences(
            "notification_pref",
            Context.MODE_PRIVATE
        )

    fun getLastShownDate(): String {

        return prefs.getString(
            "last_date",
            ""
        ) ?: ""
    }

    fun saveLastShownDate(
        date: String
    ) {

        prefs.edit()
            .putString(
                "last_date",
                date
            )
            .apply()
    }

    fun saveLoginTime(
        time: Long
    ) {

        prefs.edit()
            .putLong(
                "login_time",
                time
            )
            .apply()
    }

    fun getLoginTime(): Long {

        return prefs.getLong(
            "login_time",
            0L
        )
    }
}