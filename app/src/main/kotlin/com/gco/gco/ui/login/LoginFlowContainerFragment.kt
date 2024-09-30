package com.gco.gco.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gco.gco.MainActivity
import com.gco.gco.R
import com.gco.gco.databinding.FragmentLoginBinding
import com.gco.gco.ui.login.email_sent.EmailSentFragment
import com.gco.gco.ui.login.otp_verification.OtpVerificationFragment
import com.gco.gco.ui.login.password.forgot_password.ForgotPasswordFragment
import com.gco.gco.ui.login.password.password_changed.PasswordChangedFragment
import com.gco.gco.ui.login.password.reset_password.ResetPasswordFragment
import com.gco.gco.ui.login.sign_in.SignInFragment
import com.gco.gco.utils.BaseFragment
import com.mbt.localization.LocalizedApp
import kotlinx.coroutines.launch

class LoginFlowContainerFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private val localizationDelegate = LocalizationDelegate()
    private var animated: Boolean = false
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
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        LocalizedApp.localeLiveData.observe(viewLifecycleOwner, localizationDelegate)
        (requireActivity() as MainActivity).windowInsetsLiveData.observe(viewLifecycleOwner) { insets ->
            val rect =
                insets.getInsets(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars() or WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            binding.safeBottomGuideline.setGuidelineEnd(rect.bottom)
        }
    }

    private fun setupViews() = binding.apply {
        var fragmentToLoad: BaseFragment? = null
        loginViewModel.currentScreen.observe(viewLifecycleOwner) {
            when (it) {
                LoginPages.SignInScreen -> {
                    fragmentToLoad = SignInFragment(loginViewModel)
                }

                LoginPages.OtpVerificationScreen -> {
                    fragmentToLoad = OtpVerificationFragment(loginViewModel)
                }

                LoginPages.ForgotPasswordEmailScreen -> {
                    fragmentToLoad = ForgotPasswordFragment(loginViewModel)
                }

                LoginPages.EmailSentScreen -> {
                    fragmentToLoad = EmailSentFragment(loginViewModel)
                }

                LoginPages.ResetPasswordScreen -> {
                    fragmentToLoad = ResetPasswordFragment(loginViewModel)
                }

                LoginPages.PassWordChangedScreen -> {
                    fragmentToLoad = PasswordChangedFragment(loginViewModel)
                }

                else -> {
                    Log.e(TAG, "setupViews: un handled page")
                }
            }
            fragmentToLoad?.let {
                this@LoginFlowContainerFragment.childFragmentManager.beginTransaction().apply {
                    if (!animated)
                        setCustomAnimations(R.anim.fade_in_up, R.anim.fade_in_up)
                    animated = true
                    this.replace(fcFlowContainer.id, it, TAG)
                }.commit()
            }
        }
    }

    private inner class LocalizationDelegate : Observer<String> {
        override fun onChanged(value: String) {
            binding.apply {
            }
        }
    }
}