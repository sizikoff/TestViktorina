package ru.korben.viktorina.webview

import android.content.Context
import android.content.SharedPreferences

class Prefs private constructor() {
    lateinit var preferences: SharedPreferences

    companion object {
        private val fileName = "APP_PREFERENCES"
        fun make(context: Context): Prefs {
            val appPreferences = Prefs()
            appPreferences.preferences = context.getSharedPreferences(fileName, 0)
            return appPreferences
        }
    }
    val url: String? get() = preferences.getString("URL", "no")//no - отсутствует
    fun restore(url: String) {
        preferences.edit().putString("URL", url).apply()
    }

}