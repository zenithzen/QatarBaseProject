package com.gco.gco.ui.login.password.reset_password

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.gco.gco.R
import com.gco.gco.TypefaceLoader.setInterBold
import com.gco.gco.TypefaceLoader.setInterMedium
import com.gco.gco.TypefaceLoader.setInterRegular
import com.gco.gco.TypefaceLoader.setSemiBold
import com.gco.gco.databinding.FragmentLoginUpdatePasswordBinding
import com.gco.gco.ui.login.LoginPages
import com.gco.gco.ui.login.LoginViewModel
import com.gco.gco.utils.BaseFragment
import com.gco.gco.utils.choose
import com.gco.gco.utils.dp
import com.gco.gco.utils.setCornerRadius
import com.gco.gco.utils.setOnSafeClickListener
import com.mbt.localization.Localizable
import com.mbt.localization.LocalizedApp
import com.mbt.localization.toLocalizedString
import java.util.regex.Pattern

class ResetPasswordFragment(private val loginViewModel: LoginViewModel) : BaseFragment() {
    private lateinit var binding: FragmentLoginUpdatePasswordBinding
    private val localizationDelegate = LocalizationDelegate()
    private var isNewPasswordVisible = false
    private var isConfirmPasswordVisible = false
    private val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                loginViewModel.updateScreen(LoginPages.SignInScreen)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginUpdatePasswordBinding.inflate(layoutInflater, container, false)
        loginViewModel.password = null
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        isNewPasswordVisible = false
        isConfirmPasswordVisible = false
        updatePasswordVisibility()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        LocalizedApp.localeLiveData.observe(viewLifecycleOwner, localizationDelegate)
    }

    private fun setupViews() = binding.apply {
        root.setCornerRadius(dp(32))
        ivNewPasswordVisibility.setOnClickListener {
            isNewPasswordVisible = !isNewPasswordVisible
            updatePasswordVisibility()
        }
        ivNewPasswordVisibility.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            updatePasswordVisibility()
        }
        etNewPassword.doOnTextChanged { text, start, before, count ->
            updatePasswordCriteria()
        }

        btnReset.setOnSafeClickListener {
            if (isFormValidated()) {
                etNewPassword.clearFocus()
                etConfirmPassword.clearFocus()
                loginViewModel.updateScreen(LoginPages.PassWordChangedScreen)
                Toast.makeText(
                    requireContext(), "Password updated", Toast.LENGTH_SHORT
                ).show()
            }
        }
        btnLogin.setOnSafeClickListener {
            loginViewModel.updateScreen(LoginPages.SignInScreen)
        }
        updatePasswordCriteria()
    }

    private fun updatePasswordCriteria(): Boolean {
        var satisfiesCriteria = true
        binding.apply {
            val newPassword = etNewPassword.text.toString()
            val hasLowerCase = lowerCaseCheck(newPassword)
            val hasUpperCase = upperCaseCheck(newPassword)
            val hasNumber = numberCaseCheck(newPassword)
            val hasSpecialCharacter = hasSpecialCharactersCheck(newPassword)
            tvPasswordCriteria1UpperCase.apply {
                setTextColor(
                    choose(
                        hasUpperCase,
                        R.color.update_password_criteria_green,
                        R.color.update_password_criteria_grey
                    )
                )
                setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        requireContext(),
                        choose(
                            hasUpperCase,
                            R.drawable.update_password_criteria_tick,
                            R.drawable.update_password_criteria_bullet
                        )
                    ), null, null, null
                )
            }
            tvPasswordCriteria2LowerCase.apply {
                setTextColor(
                    choose(
                        hasLowerCase,
                        R.color.update_password_criteria_green,
                        R.color.update_password_criteria_grey
                    )
                )
                setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        requireContext(),
                        choose(
                            hasLowerCase,
                            R.drawable.update_password_criteria_tick,
                            R.drawable.update_password_criteria_bullet
                        )
                    ), null, null, null
                )
            }
            tvPasswordCriteria3Number.apply {
                setTextColor(
                    choose(
                        hasNumber,
                        R.color.update_password_criteria_green,
                        R.color.update_password_criteria_grey
                    )
                )
                setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        requireContext(),
                        choose(
                            hasNumber,
                            R.drawable.update_password_criteria_tick,
                            R.drawable.update_password_criteria_bullet
                        )
                    ), null, null, null
                )
            }
            tvPasswordCriteria4SpecialCharacters.apply {
                setTextColor(
                    choose(
                        hasSpecialCharacter,
                        R.color.update_password_criteria_green,
                        R.color.update_password_criteria_grey
                    )
                )
                setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        requireContext(),
                        choose(
                            hasSpecialCharacter,
                            R.drawable.update_password_criteria_tick,
                            R.drawable.update_password_criteria_bullet
                        )
                    ), null, null, null
                )
            }
            satisfiesCriteria = hasLowerCase && hasUpperCase && hasNumber && hasSpecialCharacter
            if (!satisfiesCriteria)
                localizationDelegate.passwordCriteriaError =
                    LocalizedApp.getString(R.string.password_criteria_not_satisfied)
                        .toLocalizedString()
            else
                localizationDelegate.passwordCriteriaError = null
        }
        return satisfiesCriteria
    }

    private fun lowerCaseCheck(input: String): Boolean {
        val regex = Pattern.compile("[a-z]")
        return regex.matcher(input).find()
    }

    private fun upperCaseCheck(input: String): Boolean {
        val regex = Pattern.compile("[A-Z]")
        return regex.matcher(input).find()
    }

    private fun numberCaseCheck(input: String): Boolean {
        val regex = Pattern.compile("[0-9]")
        return regex.matcher(input).find()
    }

    private fun hasSpecialCharactersCheck(input: String): Boolean {
        val regex = Pattern.compile("[!@#\$%^&*()-=_+\\[\\]{}|;:'\",.<>?/`~]")
        return regex.matcher(input).find()
    }


    private fun updatePasswordVisibility() = binding.apply {
        if (isNewPasswordVisible) {
            etNewPassword.apply {
                transformationMethod = HideReturnsTransformationMethod.getInstance()
                setSelection((this.text?.length ?: 0))
            }
        } else {
            etNewPassword.apply {
                transformationMethod = PasswordTransformationMethod.getInstance()
                setSelection((this.text?.length ?: 0))
            }
        }
        if (isConfirmPasswordVisible) {
            etConfirmPassword.apply {
                transformationMethod = HideReturnsTransformationMethod.getInstance()
                setSelection((this.text?.length ?: 0))
            }
        } else {
            etConfirmPassword.apply {
                transformationMethod = PasswordTransformationMethod.getInstance()
                setSelection((this.text?.length ?: 0))
            }
        }
    }

    private fun isFormValidated(): Boolean {
        var isValid = true
        binding.apply {
            if (etNewPassword.text.toString().isEmpty()) {
                localizationDelegate.newPasswordError =
                    LocalizedApp.getString(R.string.enter_password).toLocalizedString()
                isValid = false
            } else {
                if (localizationDelegate.passwordCriteriaError != null) {
                    localizationDelegate.newPasswordError =
                        localizationDelegate.passwordCriteriaError
                } else {
                    localizationDelegate.newPasswordError = null
                    localizationDelegate.passwordCriteriaError = null
                }
            }
            if (etConfirmPassword.text.toString().isEmpty()) {
                localizationDelegate.confirmPasswordError =
                    LocalizedApp.getString(R.string.enter_password).toLocalizedString()
                isValid = false
            } else {
                localizationDelegate.confirmPasswordError = null
            }
            isValid = updatePasswordCriteria()
            if (etNewPassword.text.toString() != etConfirmPassword.text.toString()) {
                if (etConfirmPassword.text.toString().isNotEmpty())
                    localizationDelegate.confirmPasswordError =
                        LocalizedApp.getString(R.string.passwords_do_not_match).toLocalizedString()
                isValid = false
            } else {
                localizationDelegate.confirmPasswordError = null
            }
        }
        localizationDelegate.update()
        return isValid
    }

    private inner class LocalizationDelegate : Observer<String> {
        var newPasswordError: Localizable<out CharSequence>? = null
        var passwordCriteriaError: Localizable<out CharSequence>? = null
        var confirmPasswordError: Localizable<out CharSequence>? = null
        fun update() {
            onChanged(LocalizedApp.locale)
        }

        override fun onChanged(value: String) {
            binding.apply {
                val resources = LocalizedApp.getResources(value)
                tvTitle.apply {
                    text = resources.getString(R.string.forgot_password)
                    setInterBold(value)
                }
                tvTitleNewPassword.apply {
                    text = resources.getString(R.string.new_password)
                    setInterMedium(value)
                }
                etNewPassword.apply {
                    hint = resources.getString(R.string.new_password)
                    setInterMedium(value)
                }
                tvNewPasswordError.apply {
                    text = newPasswordError?.getLocalizedValue(value)
//                    viewVisibility(newPasswordError != null)
                    setInterRegular(value)
                }
                tvTitleConfirmPassword.apply {
                    text = resources.getString(R.string.confirm_new_password)
                    setInterMedium(value)
                }
                etConfirmPassword.apply {
                    hint = resources.getString(R.string.confirm_new_password)
                    setInterMedium(value)
                }
                tvConfirmPasswordError.apply {
                    text = confirmPasswordError?.getLocalizedValue(value)
//                    viewVisibility(confirmPasswordError != null)
                    setInterRegular(value)
                }
                tvPasswordCriteriaTitle.apply {
                    text = resources.getString(R.string.password_criteria)
                    setSemiBold(value)
                }
                tvPasswordCriteriaTitle.apply {
                    text = resources.getString(R.string.password_criteria)
                    setSemiBold(value)
                }
                tvPasswordCriteria1UpperCase.apply {
                    text = resources.getString(R.string.uppercase_letters_a_z)
                    setInterRegular(value)
                }
                tvPasswordCriteria2LowerCase.apply {
                    text = resources.getString(R.string.lowercase_letters_a_z)
                    setInterRegular(value)
                }
                tvPasswordCriteria3Number.apply {
                    text = resources.getString(R.string.numbers_0_9)
                    setInterRegular(value)
                }
                tvPasswordCriteria4SpecialCharacters.apply {
                    text = resources.getString(R.string.special_characters_etc)
                    setInterRegular(value)
                }
                btnReset.apply {
                    text = resources.getString(R.string.reset_password)
                    setInterMedium(value)
                }
                tvLogin.apply {
                    text = resources.getString(R.string.login_now)
                    setSemiBold(value)
                }
            }
        }
    }
}