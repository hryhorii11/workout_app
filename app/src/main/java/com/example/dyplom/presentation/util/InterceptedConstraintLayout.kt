package com.example.dyplom.presentation.util

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Standard constraint layout with interceptTouch event overridden to listen to
 * every click on the screen including children views
 */
class InterceptedConstraintLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    private val touchThreshold = 15

    private var touchX: Float = 0f
    private var touchY: Float = 0f

    lateinit var onTapEvent: () -> Unit

    // returns false to listen to children touch events
    override fun onInterceptTouchEvent(motionEvent: MotionEvent?): Boolean {
        motionEvent?.let {
            if (it.action == MotionEvent.ACTION_DOWN) {
                touchX = motionEvent.x
                touchY = motionEvent.y
            } else if (touchConsideredClick(it)) {
                onTapEvent()
            }
        }
        return false
    }

    private fun touchConsideredClick(motionEvent: MotionEvent) =
        kotlin.math.abs(motionEvent.x - touchX) < touchThreshold && kotlin.math.abs(motionEvent.y - touchY) < touchThreshold

}