package com.applover.network

import com.applover.entity.LoginStatus
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object AlResponseConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {

        val wrappedType = object : ParameterizedType {
            override fun getRawType(): Type = LoginStatus::class.java
            override fun getOwnerType(): Type? = null
            override fun getActualTypeArguments(): Array<Type> = arrayOf(type)
        }
        val delegate = retrofit.nextResponseBodyConverter<Converter<ResponseBody, LoginStatus>>(
            this, wrappedType, annotations
        ) as Converter<ResponseBody, LoginStatus>

        return KcResponseConverter(delegate)
    }
}