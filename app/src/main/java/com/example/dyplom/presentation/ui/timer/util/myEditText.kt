package com.example.dyplom.presentation.ui.timer.util

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class MyEditTextView : TextInputEditText {
    constructor(context: Context) : super(context) {
    }
    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        return false
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }
}