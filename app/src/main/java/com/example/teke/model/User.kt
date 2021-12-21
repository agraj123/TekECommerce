package com.example.teke.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val name: String, val email: String, val address: String) : Parcelable