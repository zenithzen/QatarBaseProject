package com.gco.gco.ui.login.otp_verification

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.gco.gco.R
import com.gco.gco.TypefaceLoader
import com.gco.gco.TypefaceLoader.setInterBold
import com.gco.gco.TypefaceLoader.setInterMedium
import com.gco.gco.TypefaceLoader.setInterRegular
import com.gco.gco.TypefaceLoader.setSemiBold
import com.gco.gco.databinding.FragmentOtpVerificationFragmentBinding
import com.gco.gco.ui.login.LoginFlowContainerFragmentDirections
import com.gco.gco.ui.login.LoginPages
import com.gco.gco.ui.login.LoginViewModel
import com.gco.gco.utils.BaseFragment
import com.gco.gco.utils.dp
import com.gco.gco.utils.getNavOptions
import com.gco.gco.utils.setCornerRadius
import com.gco.gco.utils.setOnSafeClickListener
import com.mbt.localization.LocalizedApp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale

class OtpVerificationFragment(private val loginViewModel: LoginViewModel) : BaseFragment() {
    private val TIMER_DURATION_SECONDS = 90

    private lateinit var binding: FragmentOtpVerificationFragmentBinding
    private val localizationDelegate = LocalizationDelegate()
    private val otpVerificationViewModel: OTPVerificationViewModel by viewModels()
    private var timerJob: Job? = null
    private var secondsRemaining = TIMER_DURATION_SECONDS


    private val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                loginViewModel.updateScreen(LoginPages.SignInScreen)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otpVerificationViewModel.email = loginViewModel.email
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                //send otp api
                setupTimer()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpVerificationFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this@OtpVerificationFragment.viewLifecycleOwner, callback
        )
        setupViews()
        LocalizedApp.localeLiveData.observe(viewLifecycleOwner, localizationDelegate)
    }

    private fun setupTimer() {
        if (timerJob?.isActive != true) {
            timerJob = lifecycleScope.launchWhenResumed {
                while (isActive) {
                    updateRemainingTime()
                    if (secondsRemaining > 0) {
                        secondsRemaining -= 1
                    } else {
                        break
                    }
                    delay(1000)
                }
            }
        }
    }

    private fun updateRemainingTime() {
        val minutes = secondsRemaining / 60
        val seconds = secondsRemaining.rem(60)
        binding.timerText.text = String.format(
            Locale.US, "%d:%02d %s", minutes, seconds,
            LocalizedApp.getString(R.string.seconds)
        )
        val visibility = if (secondsRemaining > 0) View.GONE else View.VISIBLE
//        binding.tvResubmitText.visibility = visibility
    }

    private fun setupViews() = binding.apply {
        root.setCornerRadius(dp(32))
        setButtonStateDisabled()
        btnSignIn.apply {
            setCornerRadius(dp(6))
            setOnSafeClickListener {
                findNavController().navigate(
                    LoginFlowContainerFragmentDirections.actionLoginFragmentToMainFragment(),
                    getNavOptions(popFragId = R.id.loginFragment, true, true)
                )
            }
        }
        tvResendOTP.apply {
            setOnSafeClickListener {
                // resend api
                secondsRemaining = TIMER_DURATION_SECONDS
                setButtonStateDisabled()
                otpInput.setText("")
            }
        }

        otpInput.doOnTextChanged { input, _, _, _ ->
            when (val digit = input?.getOrNull(0)?.toString()) {
                null -> {
                    otpCell1.apply {
                        text = null
                    }
                }

                else -> {
                    otpCell1.apply {
                        text = digit
                    }
                }
            }
            when (val digit = input?.getOrNull(1)?.toString()) {
                null -> {
                    otpCell2.apply {
                        text = null

                    }
                }

                else -> {
                    otpCell2.apply {
                        text = digit

                    }
                }
            }
            when (val digit = input?.getOrNull(2)?.toString()) {
                null -> {
                    otpCell3.apply {
                        text = null

                    }
                }

                else -> {
                    otpCell3.apply {
                        text = digit

                    }
                }
            }
            when (val digit = input?.getOrNull(3)?.toString()) {
                null -> {
                    otpCell4.apply {
                        text = null

                    }
                }

                else -> {
                    otpCell4.apply {
                        text = digit

                    }
                }
            }
            if (input?.length == 4) {
                setButtonStateEnabled()
            } else {
                setButtonStateDisabled()
            }
        }
    }

    private fun setButtonStateDisabled() {
        binding.btnSignIn.apply {
            isClickable = false
            setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.otp_button_disabled
                )
            )
        }
    }

    private fun setButtonStateEnabled() {
        binding.btnSignIn.apply {
            isClickable = true
            setBackgroundColor(Color.parseColor("#83273C"))
        }
    }

    private inner class LocalizationDelegate : Observer<String> {
        override fun onChanged(value: String) {
            binding.apply {
                val resources = LocalizedApp.getResources(value)
                tvMainTitle.apply {
                    text = resources.getString(R.string.enter_the_code)
                    setInterBold(value)
                }
                tvSubTitle.apply {
                    val spannable = SpannableStringBuilder().apply {
                        this.append(
                            String.format(
                                resources.getString(R.string.we_have_sent_otp_to_your_registered_email_address_your_email),
                                otpVerificationViewModel.email!!
                            )
                        )
                    }
                    setInterRegular(value)
                    spannable.setSpan(
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            TypefaceSpan(TypefaceLoader.interSemiBold(value))
                        } else {
                            StyleSpan(Typeface.BOLD)
                        },
                        (spannable.length - otpVerificationViewModel.email!!.length) - 1,
                        spannable.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    text = spannable
                }
                tvResubmitText.apply {
                    text = resources.getString(R.string.didn_t_receive_the_otp_yet)
                    setInterMedium(value)
                }
                tvResendOTP.apply {
                    text = resources.getString(R.string.re_send)
                    setSemiBold(value)
                }
                btnSignIn.apply {
                    text = resources.getString(R.string.sign_in)
                    setInterMedium(value)
                }
            }
        }
    }
}