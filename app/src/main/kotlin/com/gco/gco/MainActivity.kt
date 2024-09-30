package com.gco.gco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import com.gco.gco.databinding.ActivityMainBinding
import com.gco.gco.utils.BaseActivity

private const val TAG = "MainActivity"

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    val windowInsetsLiveData = MutableLiveData<WindowInsetsCompat>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView.rootView) { _, insets ->
            Log.d(TAG, "post insets")
            windowInsetsLiveData.postValue(insets)
            return@setOnApplyWindowInsetsListener WindowInsetsCompat.CONSUMED

        }
    }
}