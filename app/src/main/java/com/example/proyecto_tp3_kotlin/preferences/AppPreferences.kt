package com.example.proyecto_tp3_kotlin.preferences
import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun setDarkModeEnabled(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean("dark_mode_enabled", isDarkMode).apply()
    }

    fun isDarkModeEnabled(): Boolean {
        return sharedPreferences.getBoolean("dark_mode_enabled", false)
    }
}