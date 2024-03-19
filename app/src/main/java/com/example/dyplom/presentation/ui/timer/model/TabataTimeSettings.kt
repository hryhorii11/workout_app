package com.example.dyplom.presentation.ui.timer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TabataTimeSettings(
    val preparationTime: Int,
    val workTime: Int,
    val restTime: Int,
    val roundsNumber: Int
) : Parcelable

