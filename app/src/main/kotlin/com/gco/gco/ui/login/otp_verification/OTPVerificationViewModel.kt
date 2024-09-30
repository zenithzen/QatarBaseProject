package com.gco.gco.ui.login.otp_verification

import androidx.lifecycle.ViewModel

class OTPVerificationViewModel : ViewModel() {
    var email: String? = null
    var otp: String? = null
    var seconds: String? = null
}