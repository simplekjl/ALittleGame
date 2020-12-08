package com.simplekjl.fallingwords.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Word(
    @SerializedName("text_eng")
    val value: String,
    @SerializedName("text_spa")
    val translation: String
) : Parcelable
