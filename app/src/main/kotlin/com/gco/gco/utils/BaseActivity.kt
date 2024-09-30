package com.gco.gco.utils

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mbt.utils.ActivityResultConsumer
import com.mbt.utils.IntentConsumer
import com.mbt.utils.PermissionResultConsumer
private const val TAG="BaseActivity"
abstract class BaseActivity : AppCompatActivity() {
    internal val intentConsumers = mutableListOf<IntentConsumer>()
    internal val activityResultConsumers = mutableListOf<ActivityResultConsumer>()
    internal val permissionResultConsumers = mutableListOf<PermissionResultConsumer>()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "${this::class.qualifiedName} onCreate(Bundle)")
        intentConsumers.forEach { it.consumeIntent(intent) }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.d(TAG, "${this::class.qualifiedName} onCreate(Bundle, PersistableBundle)")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.d(TAG, "${this::class.qualifiedName} onPostCreate(Bundle)")
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        Log.d(TAG, "${this::class.qualifiedName} onPostCreate(Bundle, PersistableBundle)")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "${this::class.qualifiedName} onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "${this::class.qualifiedName} onResume")
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        Log.d(TAG, "${this::class.qualifiedName} onResumeFragments")
    }

    override fun onPostResume() {
        super.onPostResume()
        Log.d(TAG, "${this::class.qualifiedName} onPostResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "${this::class.qualifiedName} onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "${this::class.qualifiedName} onStop")
    }

    override fun onDestroy() {
        intentConsumers.clear()
        activityResultConsumers.clear()
        permissionResultConsumers.clear()
        super.onDestroy()
        Log.d(TAG, "${this::class.qualifiedName} onDestroy")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(TAG, "${this::class.qualifiedName} onDetachedFromWindow")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            intentConsumers.forEach { it.consumeIntent(intent) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityResultConsumers.forEach { it.consumeActivityResult(requestCode, resultCode, data) }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionResultConsumers.forEach { it.consumePermissionResult(requestCode, permissions, grantResults) }
    }

    fun registerActivityIntentConsumer(consumer: IntentConsumer) {
        intentConsumers.add(consumer)
    }

    fun removeActivityIntentConsumer(consumer: IntentConsumer) {
        intentConsumers.remove(consumer)
    }

    fun registerActivityResultConsumer(consumer: ActivityResultConsumer) {
        activityResultConsumers.add(consumer)
    }

    fun removeActivityResultConsumer(consumer: ActivityResultConsumer) {
        activityResultConsumers.remove(consumer)
    }

    fun registerPermissionResultConsumer(consumer: PermissionResultConsumer) {
        permissionResultConsumers.add(consumer)
    }

    fun removePermissionResultConsumer(consumer: PermissionResultConsumer) {
        permissionResultConsumers.remove(consumer)
    }
}