package com.example.codechallengedrop.ui.components

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import com.example.codechallengedrop.extension.percentToValue
import com.example.codechallengedrop.extension.valueToPercente

class ProgressComponent(
    context: Context?,
    attrs: AttributeSet?
) : View(context, attrs) {

    private val ovalSpace = RectF()
    private val parentArcColor = Color.WHITE
    private val fillArcColor = Color.BLUE
    private val ballColor = Color.GREEN
    private var currentPercentage = 0
    var onCallBackValue: ((value: Int) -> Unit)? = null

    private val parentArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = parentArcColor
        strokeWidth = 20f
        strokeCap = Paint.Cap.ROUND
    }

    private val fillArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = fillArcColor
        strokeWidth = 20f
        strokeCap = Paint.Cap.ROUND
    }

    private val ballPaint = Paint().apply {
        isAntiAlias = false
        color = ballColor
        strokeWidth = 30f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setSpace()
        canvas?.let {
            drawBackgroundArc(it)
            drawInnerArc(it)
//            drawBall(it)
        }
    }

    private fun drawBackgroundArc(it: Canvas) {
        it.drawArc(ovalSpace, START, END, false, parentArcPaint)
    }

    private fun drawInnerArc(canvas: Canvas) {
        val percentageToFill = getCurrentPercentageToFill()
        canvas.drawArc(ovalSpace, START, percentageToFill, false, fillArcPaint)
    }

    private fun drawBall(canvas: Canvas) {
        canvas.drawOval(ovalSpace, ballPaint)
    }

    private fun getCurrentPercentageToFill() =
        (ARC_FULL_ROTATION_DEGREE * (currentPercentage / PERCENTAGE_DIVIDER)).toFloat() // Necessário avaliar

    private fun setSpace() {
        val horizontalCenter = (width.div(2)).toFloat()
        val verticalCenter = (height.div(2)).toFloat()
        val ovalSize = 350
        ovalSpace.set(
            horizontalCenter - ovalSize,
            verticalCenter - ovalSize,
            horizontalCenter + ovalSize,
            verticalCenter + ovalSize
        )
    }

    fun animateProgress(timeBalance: List<Pair<Int, Int>>, index: Int = 0, lastValue: Float = 0f) {
        val valuesHolder = PropertyValuesHolder.ofFloat(
            PERCENTAGE_VALUE_HOLDER,
            lastValue,
            timeBalance[index].second.valueToPercente()
        )
        val newLastValue = timeBalance[index].second.valueToPercente()
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            duration = timeBalance[index].first.toLong()
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                val percentage = it.getAnimatedValue(PERCENTAGE_VALUE_HOLDER) as Float
                currentPercentage = percentage.toInt()
                onCallBackValue?.invoke(percentage.percentToValue())
                invalidate()
            }
            doOnEnd {
                if (timeBalance.lastIndex != index) {
                    animateProgress(timeBalance, index.plus(1), newLastValue)
                }
            }
        }
        animator.start()
    }

    companion object {
        const val START: Float = 130f
        const val END: Float = 280f
        const val PERCENTAGE_VALUE_HOLDER = "percentage"
        const val ARC_FULL_ROTATION_DEGREE = 280 // Necessário avaliar
        const val PERCENTAGE_DIVIDER = 100.0 // Necessário avaliar
    }
}