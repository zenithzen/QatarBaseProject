package com.gco.gco.ui.login.email_sent

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.gco.gco.R
import com.gco.gco.TypefaceLoader
import com.gco.gco.TypefaceLoader.setInterBold
import com.gco.gco.TypefaceLoader.setInterRegular
import com.gco.gco.TypefaceLoader.setSemiBold
import com.gco.gco.databinding.FragmentLoginEmailSentBinding
import com.gco.gco.ui.login.LoginPages
import com.gco.gco.ui.login.LoginViewModel
import com.gco.gco.utils.BaseFragment
import com.gco.gco.utils.dp
import com.gco.gco.utils.setCornerRadius
import com.gco.gco.utils.setOnSafeClickListener
import com.mbt.localization.LocalizedApp

class EmailSentFragment(private val loginViewModel: LoginViewModel) : BaseFragment() {
    private lateinit var binding: FragmentLoginEmailSentBinding
    private val localizationDelegate = LocalizationDelegate()
    private val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                loginViewModel.updateScreen(LoginPages.SignInScreen)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginEmailSentBinding.inflate(layoutInflater, container, false)
        loginViewModel.password = null
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        LocalizedApp.localeLiveData.observe(viewLifecycleOwner, localizationDelegate)
    }

    private fun setupViews() = binding.apply {
        root.setCornerRadius(dp(32))
        btnDone.setOnSafeClickListener {
            loginViewModel.updateScreen(LoginPages.ResetPasswordScreen)
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
                    text = resources.getString(R.string.email_is_sent)
                    setInterBold(value)
                }
                tvSubTitle.apply {
                    val string = String.format(
                        resources.getString(R.string.we_have_sent_an_email_to_email_with_a_link_to_reset_your_password),
                        loginViewModel.email!!
                    )
                    val mailStart = string.indexOf(loginViewModel.email!!)
                    val mailEnd = mailStart + loginViewModel.email!!.length
                    val spannable = SpannableStringBuilder(string)
                    setInterRegular(value)
                    spannable.setSpan(
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            TypefaceSpan(TypefaceLoader.interSemiBold(value))

                        } else {
                            StyleSpan(Typeface.BOLD).apply {
                            }
                        },
                        mailStart,
                        mailEnd,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    spannable.setSpan(
                        ForegroundColorSpan(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.email_sent_color
                            )
                        ),
                        mailStart,
                        mailEnd,
                        SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    text = spannable
                }
                btnDone.apply {
                    text = resources.getString(R.string.done)
                    setSemiBold(value)
                }
            }
        }
    }
}