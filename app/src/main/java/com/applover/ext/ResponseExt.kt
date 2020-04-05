package com.applover.ext

import com.applover.entity.LoginStatus
import retrofit2.Response
import java.lang.Exception

fun <A : Any> Response<A>.bodyOrException(): A {
    val body = body()
    return if (isSuccessful && (body != null)) {
        if (body is LoginStatus) {
            if (body.timeout != 0f) throw Exception("Request timeout")
        }
        body
    } else {
        throw Exception(message())
    }
}