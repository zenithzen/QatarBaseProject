package com.gco.gco.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _currentScreen: MutableLiveData<LoginPages> =
        MutableLiveData(LoginPages.SignInScreen)
    val currentScreen: LiveData<LoginPages> get() = _currentScreen
    var email: String? = null
    var password: String? = null

    fun updateScreen(screen: LoginPages) {
        _currentScreen.postValue(screen)
    }

}