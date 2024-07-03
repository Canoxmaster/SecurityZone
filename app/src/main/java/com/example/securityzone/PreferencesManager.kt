package com.example.securityzone

import android.content.Context

class PreferencesManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("SecurityZonePrefs", Context.MODE_PRIVATE)

    var isBlocked: Boolean
        get() = sharedPreferences.getBoolean("isBlocked", true)
        set(value) {
            sharedPreferences.edit().putBoolean("isBlocked", value).apply()
        }
}