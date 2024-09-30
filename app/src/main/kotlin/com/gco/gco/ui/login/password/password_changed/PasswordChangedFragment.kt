package com.gco.gco.ui.login.password.password_changed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.gco.gco.R
import com.gco.gco.TypefaceLoader.setInterBold
import com.gco.gco.TypefaceLoader.setInterRegular
import com.gco.gco.TypefaceLoader.setSemiBold
import com.gco.gco.databinding.FragmentLoginPasswordChangedBinding
import com.gco.gco.ui.login.LoginPages
import com.gco.gco.ui.login.LoginViewModel
import com.gco.gco.utils.BaseFragment
import com.gco.gco.utils.dp
import com.gco.gco.utils.setCornerRadius
import com.gco.gco.utils.setOnSafeClickListener
import com.mbt.localization.LocalizedApp

class PasswordChangedFragment(private val loginViewModel: LoginViewModel) : BaseFragment() {
    private lateinit var binding: FragmentLoginPasswordChangedBinding
    private val localizationDelegate = LocalizationDelegate()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginPasswordChangedBinding.inflate(layoutInflater, container, false)
        loginViewModel.password = null
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        LocalizedApp.localeLiveData.observe(viewLifecycleOwner, localizationDelegate)
    }

    private fun setupViews() = binding.apply {
        root.setCornerRadius(dp(32))
        btnLogin.setOnSafeClickListener {
            loginViewModel.updateScreen(LoginPages.SignInScreen)
        }
    }

    private inner class LocalizationDelegate : Observer<String> {
        fun update() {
            onChanged(LocalizedApp.locale)
        }

        override fun onChanged(value: String) {
            binding.apply {
                val resources = LocalizedApp.getResources(value)
                tvTitle.apply {
                    text = resources.getString(R.string.password_is_changed)
                    setInterBold(value)
                }
                tvSubTitle.apply {
//                    text = resources.getString(R.string.password_is_changed)
                    setInterRegular(value)
                }
                btnLogin.apply {
                    text = resources.getString(R.string.login_now)
                    setSemiBold(value)
                }
            }
        }
    }
}