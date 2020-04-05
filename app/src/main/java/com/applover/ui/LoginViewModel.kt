package com.applover.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applover.common.customs.SingleLiveEvent
import com.applover.entity.LoginStatus
import com.applover.entity.UserCredentials
import com.applover.network.Result
import com.applover.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _loginStatus: SingleLiveEvent<Result<LoginStatus>> by lazy { SingleLiveEvent<Result<LoginStatus>>() }
    val loginStatus = _loginStatus

    fun signIn(userCredentials: UserCredentials) {
        viewModelScope.launch {
            repository.signIn(userCredentials).also {
                _loginStatus.value = it
            }
        }
    }
}