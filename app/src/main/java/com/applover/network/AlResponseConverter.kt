package com.applover.network

import okhttp3.ResponseBody
import retrofit2.Converter

class KcResponseConverter<T>(
    private val delegate: Converter<ResponseBody, T>
) : Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T? = delegate.convert(value)
}
