package com.gco.gco.ui.login.password.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.util.PatternsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gco.gco.R
import com.gco.gco.TypefaceLoader.setInterBold
import com.gco.gco.TypefaceLoader.setInterMedium
import com.gco.gco.TypefaceLoader.setInterRegular
import com.gco.gco.TypefaceLoader.setSemiBold
import com.gco.gco.databinding.FragmentLoginForgotPasswordBinding
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
import kotlinx.coroutines.launch

class ForgotPasswordFragment(private val loginViewModel: LoginViewModel) : BaseFragment() {
    private lateinit var binding: FragmentLoginForgotPasswordBinding
    private val localizationDelegate = LocalizationDelegate()
    private val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                loginViewModel.updateScreen(LoginPages.SignInScreen)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginForgotPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        setupViews()
        LocalizedApp.localeLiveData.observe(viewLifecycleOwner, localizationDelegate)
    }

    private fun setupViews() = binding.apply {
        root.setCornerRadius(dp(32f))
        tvSubmit.setOnSafeClickListener {
            if (isFormValidated()) {
                loginViewModel.email = etEmail.text.toString()
                loginViewModel.updateScreen(LoginPages.EmailSentScreen)
            }
        }
        tvGoBack.setOnSafeClickListener {
            loginViewModel.updateScreen(LoginPages.SignInScreen)
        }
    }

    private fun isFormValidated(): Boolean {
        var isValid = true
        binding.apply {
            loginViewModel.email = etEmail.text.toString()
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
        }
        localizationDelegate.update()
        return isValid
    }

    private inner class LocalizationDelegate : Observer<String> {
        var emailError: Localizable<out CharSequence>? = null
        fun update() {
            this.onChanged(LocalizedApp.locale)
        }

        override fun onChanged(value: String) {
            binding.apply {
                val resources = LocalizedApp.getResources(value)
                tvMainTitle.apply {
                    text = resources.getString(R.string.title_forgot_password)
                    setInterBold(value)
                }
                tvSubTitle.apply {
                    text =
                        "Lorem ipsum dolor sit amet consectetur volut viverra ipsum tincidunt et diam."///resources.getString(R.string.sign_in_to_gco_news_room)
                    setInterRegular(value)
                }
                tvUserNameTitle.apply {
                    text = resources.getString(R.string.enter_your_registered_username)
                    setInterRegular(value)
                }
                etEmail.apply {
                    hint = resources.getString(R.string.email)
                    setText(loginViewModel.email)
                    setSelection(text?.length ?: 0)
                    setInterRegular(value)
                }
                tvEmailError.apply {
                    text = emailError?.getLocalizedValue(value)
                    viewVisibility(emailError != null)
                    setInterRegular(value)
                }
                tvSubmit.apply {
                    text = resources.getString(R.string.submit)
                    setInterMedium(value)
                }
                tvGoBack.apply {
                    text = resources.getString(R.string.go_back)
                    setSemiBold(value)
                }
            }
        }
    }
}