package com.example.dyplom.presentation.ui.timer.ext

fun String.toSeconds(): Int {
    val parts = this.split(":")
    val minutes = parts[0].toInt()
    val seconds = parts[1].toInt()
    return minutes * 60 + seconds
}

fun Long.toTimeFormat(): String {
    var minutes = (this / 60).toString()
    var seconds = (this % 60).toString()
    if (Integer.parseInt(minutes) < 10) minutes = "0$minutes"
    if (Integer.parseInt(seconds) < 10) seconds = "0$seconds"
    return "$minutes:$seconds"
}


