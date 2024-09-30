package com.gco.gco.ui.splash

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.gco.gco.Authorization
import com.gco.gco.R
import com.gco.gco.databinding.FragmentSplashBinding
import com.gco.gco.utils.BaseFragment
import com.gco.gco.utils.getNavOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment() {
    private lateinit var binding: FragmentSplashBinding
    private val TAG = "SplashFragment"
    private val splashTime = 800L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        animateAndNavigate()
    }

    private fun animateAndNavigate() {
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                animateSplash()
                delay(splashTime)
                if (Authorization.token == null) {
                    findNavController().navigate(
                        SplashFragmentDirections.actionSplashFragmentToLoginFragment(),
                        getNavOptions(
                            R.id.splashFragment, popFragInclusive = true, singleTop = true
                        )
                    )
                } else {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
                }
            }
        }
    }

    private fun animateSplash() = binding.apply {
        val splashIconAnimator = ValueAnimator.ofFloat(1f, 3.5f).setDuration(splashTime - 100)
        splashIconAnimator.addUpdateListener {
            ivSplashLogo.scaleX = it.animatedValue as Float
            ivSplashLogo.scaleY = it.animatedValue as Float
        }
        splashIconAnimator.start()
    }
}