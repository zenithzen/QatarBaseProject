package com.gco.gco.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment


open class BaseFragment : Fragment {
    companion object {
        const val TAG = "BaseFragment"
    }

    constructor() : super()
    constructor(@LayoutRes id: Int) : super(id)

    var isStateInitialized: Boolean = false
        private set
    var isVersionCheckEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onViewStateRestored")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onAttach")
        super.onAttach(context)
    }

    override fun onDestroyView() {
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        isStateInitialized = false
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onDetach")
        super.onDetach()
    }

    override fun onStart() {
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onStart")
        super.onStart()
    }

    override fun onResume() {
        isStateInitialized = true
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onStop")
        super.onStop()
    }

    override fun onLowMemory() {
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onLowMemory")
        super.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "${this::class.qualifiedName} ${toString()} onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }
}