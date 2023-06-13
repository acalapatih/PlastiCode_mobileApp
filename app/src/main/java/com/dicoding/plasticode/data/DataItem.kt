package com.dicoding.plasticode.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataItem(
    val id: Int,
    val img: String,
    val name: String
): Parcelable