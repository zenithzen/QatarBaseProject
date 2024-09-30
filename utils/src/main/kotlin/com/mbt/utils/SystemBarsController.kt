package com.mbt.utils

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity

interface SystemBarController {
    var isLightStatusBar: Boolean
    var isLightNavigationBar: Boolean
}

@RequiresApi(30)
class SystemBarControllerImplV30(private val activity: FragmentActivity): SystemBarController {
    override var isLightStatusBar: Boolean
        get() {
            val flags = activity.window.insetsController?.systemBarsAppearance
            if (flags != null) {
                return FlagUtils.containsFlag(flags, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            }
            return false
        }
        set(value) {
            val flags = WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            activity.window.insetsController?.setSystemBarsAppearance(if (value) flags else 0, flags)
        }
    override var isLightNavigationBar: Boolean
        get() {
            val flags = activity.window.insetsController?.systemBarsAppearance
            if (flags != null) {
                return FlagUtils.containsFlag(flags, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)
            }
            return false
        }
        set(value) {
            val flags = WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            activity.window.insetsController?.setSystemBarsAppearance(if (value) flags else 0, flags)
        }
}

@RequiresApi(26)
@Suppress("DEPRECATION")
class SystemBarControllerImplV26(private val activity: FragmentActivity): SystemBarController {
    override var isLightStatusBar: Boolean
        get() = FlagUtils.containsFlag(
            activity.window.decorView.systemUiVisibility,
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        )
        set(value) {
            val flags = activity.window.decorView.systemUiVisibility
            activity.window.decorView.systemUiVisibility = if (value) {
                FlagUtils.addFlag(flags, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            } else {
                FlagUtils.removeFlag(flags, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            }
        }
    override var isLightNavigationBar: Boolean
        get() = FlagUtils.containsFlag(
            activity.window.decorView.systemUiVisibility,
            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        )
        set(value) {
            val flags = activity.window.decorView.systemUiVisibility
            activity.window.decorView.systemUiVisibility = if (value) {
                FlagUtils.addFlag(flags, View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
            } else {
                FlagUtils.removeFlag(flags, View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
            }
        }
}

@RequiresApi(23)
@Suppress("DEPRECATION", "UNUSED_PARAMETER")
class SystemBarControllerImplV23(private val activity: FragmentActivity): SystemBarController {
    override var isLightStatusBar: Boolean
        get() = FlagUtils.containsFlag(
            activity.window.decorView.systemUiVisibility,
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        )
        set(value) {
            val flags = activity.window.decorView.systemUiVisibility
            activity.window.decorView.systemUiVisibility = if (value) {
                FlagUtils.addFlag(flags, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            } else {
                FlagUtils.removeFlag(flags, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            }
        }
    override var isLightNavigationBar: Boolean
        get() = false
        set(value) {}
}

@Suppress("UNUSED_PARAMETER")
class SystemBarControllerImplPreV23(): SystemBarController {
    override var isLightStatusBar: Boolean
        get() = false
        set(value) {}
    override var isLightNavigationBar: Boolean
        get() = false
        set(value) {}
}

val FragmentActivity.systemBarController: SystemBarController
    @SuppressLint("NewApi")
    get() {
        return when(Build.VERSION.SDK_INT) {
            in 30..Int.MAX_VALUE -> SystemBarControllerImplV30(this)
            in 26..29 -> SystemBarControllerImplV26(this)
            in 23..25 -> SystemBarControllerImplV23(this)
            else -> SystemBarControllerImplPreV23()
        }
    }