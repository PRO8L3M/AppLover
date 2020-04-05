package com.applover.network

import com.applover.entity.LoginStatus
import com.applover.entity.UserCredentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AppLoverApi {

    @POST("api/v1/login/")
    suspend fun signInUser(@Body userCredentials: UserCredentials): Response<LoginStatus>
}