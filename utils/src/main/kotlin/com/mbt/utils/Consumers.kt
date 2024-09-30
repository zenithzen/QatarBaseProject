package com.mbt.utils

import android.content.Intent

fun interface IntentConsumer {
    fun consumeIntent(intent: Intent)
}

fun interface ActivityResultConsumer {
    fun consumeActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}

fun interface PermissionResultConsumer {
    fun consumePermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    )
}