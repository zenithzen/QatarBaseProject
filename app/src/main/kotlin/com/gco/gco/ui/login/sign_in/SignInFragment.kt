package com.gco.gco.ui.login.sign_in

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.PatternsCompat
import androidx.lifecycle.Observer
import com.gco.gco.R
import com.gco.gco.TypefaceLoader.setInterBold
import com.gco.gco.TypefaceLoader.setInterMedium
import com.gco.gco.TypefaceLoader.setInterRegular
import com.gco.gco.TypefaceLoader.setSemiBold
import com.gco.gco.databinding.FragmentLoginSignInBinding
import com.gco.gco.ui.login.LoginPages
import com.gco.gco.ui.login.LoginViewModel
import com.gco.gco.utils.BaseFragment
import com.gco.gco.utils.dp
import com.gco.gco.utils.setCornerRadius
import com.gco.gco.utils.setOnSafeClickListener
import com.gco.gco.utils.viewVisibility
import com.mbt.localization.Localizable
import com.mbt.localization.LocalizedApp
import com.mbt.localization.toLocalizedString

class SignInFragment(private val loginViewModel: LoginViewModel) : BaseFragment() {
    private lateinit var binding: FragmentLoginSignInBinding
    private val localizationDelegate = LocalizationDelegate()
    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginSignInBinding.inflate(layoutInflater, container, false)
        loginViewModel.password = null
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        isPasswordVisible = false
        updatePasswordVisibility()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        LocalizedApp.localeLiveData.observe(viewLifecycleOwner, localizationDelegate)
    }

    private fun setupViews() = binding.apply {
        root.setCornerRadius(dp(32))
        ivPasswordVisibility.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            updatePasswordVisibility()
        }
        btnContinue.setOnSafeClickListener {
            if (isFormValidated()) {
                etPassword.clearFocus()
                etEmail.clearFocus()
                loginViewModel.updateScreen(LoginPages.OtpVerificationScreen)
            }
        }
        tvForgotPassword.setOnSafeClickListener {
            loginViewModel.email = etEmail.text.toString()
            loginViewModel.updateScreen(LoginPages.ForgotPasswordEmailScreen)
        }
    }

    private fun updatePasswordVisibility() = binding.apply {
        if (isPasswordVisible) {
            etPassword.apply {
                transformationMethod = HideReturnsTransformationMethod.getInstance()
                setSelection((etPassword.text?.length ?: 0))
            }
        } else {
            etPassword.apply {
                transformationMethod = PasswordTransformationMethod.getInstance()
                setSelection((etPassword.text?.length ?: 0))
            }
        }
    }

    private fun isFormValidated(): Boolean {
        var isValid = true
        binding.apply {
            loginViewModel.email = etEmail.text.toString()
            loginViewModel.password = etPassword.text.toString()
            if (loginViewModel.email.isNullOrEmpty()) {
                localizationDelegate.emailError =
                    LocalizedApp.getString(R.string.please_enter_email).toLocalizedString()
                isValid = false
            } else {
                if (!PatternsCompat.EMAIL_ADDRESS.matcher(loginViewModel.email!!).matches()) {
                    localizationDelegate.emailError =
                        LocalizedApp.getString(R.string.please_enter_valid_email)
                            .toLocalizedString()
                    isValid = false
                } else {
                    localizationDelegate.emailError = null
                }
            }
            if (loginViewModel.password.isNullOrEmpty()) {
                loginViewModel.password = etPassword.text.toString()
                localizationDelegate.passwordError =
                    LocalizedApp.getString(R.string.enter_password).toLocalizedString()
                isValid = false
            } else {
                localizationDelegate.passwordError = null
            }
        }
        localizationDelegate.update()
        return isValid
    }

    private inner class LocalizationDelegate : Observer<String> {
        var emailError: Localizable<out CharSequence>? = null
        var passwordError: Localizable<out CharSequence>? = null
        fun update() {
            onChanged(LocalizedApp.locale)
        }

        override fun onChanged(value: String) {
            binding.apply {
                val resources = LocalizedApp.getResources(value)
                tvSignInTitle.apply {
                    text = resources.getString(R.string.sign_in_to_gco_news_room)
                    setInterBold(value)
                }
                tvSignInSubTitle.apply {
                    text =
                        "Lorem ipsum dolor sit amet consectetur volut viverra ipsum tincidunt et diam."///resources.getString(R.string.sign_in_to_gco_news_room)
                    setInterRegular(value)
                }
                tvTitleEmail.apply {
                    text = resources.getString(R.string.email_address)
                    setInterRegular(value)
                }
                etEmail.apply {
                    hint = resources.getString(R.string.email)
                    setText(loginViewModel.email)
                    etEmail.setSelection((etEmail.text?.length ?: 0))
                    setInterRegular(value)
                }
                tvEmailError.apply {
                    text = emailError?.getLocalizedValue(value)
                    viewVisibility(emailError != null)
                    setInterRegular(value)
                }
                etPassword.apply {
                    hint = resources.getString(R.string.password)
                    setText(loginViewModel.password)
                    setInterRegular(value)
                }
                tvPasswordError.apply {
                    text = passwordError?.getLocalizedValue(value)
                    viewVisibility(passwordError != null)
                    setInterRegular(value)
                }
                btnContinue.apply {
                    text = resources.getString(R.string.btn_continue)
                    setInterMedium(value)
                }
                tvForgotPassword.apply {
                    text = resources.getString(R.string.forgot_password)
                    setSemiBold(value)
                }
            }
        }
    }
}