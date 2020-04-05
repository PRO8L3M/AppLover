package com.applover.repository

import com.applover.entity.LoginStatus
import com.applover.entity.UserCredentials
import com.applover.ext.bodyOrException
import com.applover.network.AppLoverApi
import com.applover.network.Result
import com.applover.network.safeCall

class LoginRepository(private val api: AppLoverApi) {

 suspend fun signIn(userCredentials: UserCredentials): Result<LoginStatus> = safeCall { api.signInUser(userCredentials).bodyOrException() }
}
