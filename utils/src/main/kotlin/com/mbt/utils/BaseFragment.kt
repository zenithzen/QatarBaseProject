package com.mbt.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment

private const val TAG = "BaseFragment"

abstract class BaseFragment : Fragment() {
    private val intentConsumers = mutableListOf<IntentConsumer>()
    private val activityResultConsumers = mutableListOf<ActivityResultConsumer>()
    private val permissionResultConsumers = mutableListOf<PermissionResultConsumer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "${this::class.qualifiedName} onCreate")
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "${this::class.qualifiedName} onCreateView")
        val activity = requireActivity() as BaseActivity
        activity.intentConsumers.addAll(intentConsumers)
        activity.activityResultConsumers.addAll(activityResultConsumers)
        activity.permissionResultConsumers.addAll(permissionResultConsumers)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as BaseActivity
        activity.intentConsumers.addAll(intentConsumers)
        activity.activityResultConsumers.addAll(activityResultConsumers)
        activity.permissionResultConsumers.addAll(permissionResultConsumers)
        Log.d(TAG, "${this::class.qualifiedName} onViewCreated")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "${this::class.qualifiedName} onAttach")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "${this::class.qualifiedName} onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "${this::class.qualifiedName} onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "${this::class.qualifiedName} onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "${this::class.qualifiedName} onStop")
    }

    override fun onDestroyView() {
        val activity = requireActivity() as BaseActivity
        activity.intentConsumers.removeAll(intentConsumers)
        activity.activityResultConsumers.removeAll(activityResultConsumers)
        activity.permissionResultConsumers.removeAll(permissionResultConsumers)
        super.onDestroyView()
        Log.d(TAG, "${this::class.qualifiedName} onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "${this::class.qualifiedName} onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "${this::class.qualifiedName} onDetach")
    }

    fun registerActivityIntentConsumer(consumer: IntentConsumer) {
        val activity = requireActivity() as BaseActivity
        intentConsumers.add(consumer)
        activity.intentConsumers.add(consumer)
    }

    fun removeActivityIntentConsumer(consumer: IntentConsumer) {
        val activity = requireActivity() as BaseActivity
        intentConsumers.remove(consumer)
        activity.intentConsumers.remove(consumer)
    }

    fun registerActivityResultConsumer(consumer: ActivityResultConsumer) {
        val activity = requireActivity() as BaseActivity
        activityResultConsumers.add(consumer)
        activity.activityResultConsumers.add(consumer)
    }

    fun removeActivityResultConsumer(consumer: ActivityResultConsumer) {
        val activity = requireActivity() as BaseActivity
        activityResultConsumers.remove(consumer)
        activity.activityResultConsumers.remove(consumer)
    }

    fun registerPermissionResultConsumer(consumer: PermissionResultConsumer) {
        val activity = requireActivity() as BaseActivity
        permissionResultConsumers.add(consumer)
        activity.permissionResultConsumers.add(consumer)
    }

    fun removePermissionResultConsumer(consumer: PermissionResultConsumer) {
        val activity = requireActivity() as BaseActivity
        permissionResultConsumers.remove(consumer)
        activity.permissionResultConsumers.remove(consumer)
    }
}