package com.example.dyplom.presentation.ui.timer.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet


class TimerFrameView(
    context: Context,
    attributeSet: AttributeSet,
) : androidx.appcompat.widget.AppCompatTextView(context, attributeSet) {
    private var progress: Float = 0f
    private val circlePaint = Paint()
    private val linePaint = Paint()
    private val rectF = RectF()

    init {
        circlePaint.color = Color.BLACK
        circlePaint.style = Paint.Style.STROKE
        circlePaint.strokeWidth = 20f

        linePaint.color = Color.RED
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = 20f

    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width / 2f) - 10
        rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
        canvas.drawCircle(centerX, centerY, radius, circlePaint)
        val startAngle = 180f
        val sweepAngle = progress * 360f
        canvas.drawArc(rectF, startAngle, sweepAngle, false, linePaint)
    }

}