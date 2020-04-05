package com.applover.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginStatus(val timeout: Float, val status: String, val token: String): Parcelable
