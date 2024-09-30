package com.gco.gco

import android.content.SharedPreferences

object Authorization {
    private const val TAG = "Authorization"
    private const val PREF_NAME = "authorization"
    private const val KEY_TOKEN = "token"
    private lateinit var authPreferences: SharedPreferences
    var token: String? = null

}