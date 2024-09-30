package com.mbt.localization

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

private const val TAG = "LocaleReceiver"

class LocaleReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            when (val locale = intent.getStringExtra("lang")) {
                LocalizedApp.LOCALE_EN,
                LocalizedApp.LOCALE_AR -> {
                    LocalizedApp.updateLocale(locale)
                }
                else -> {
                    Log.e(TAG, "Unregistered locale: $locale")
                }
            }
        }
    }
}