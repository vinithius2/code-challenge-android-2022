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
    private val fillArcColor = Color.MAGENTA
    private val finishArcColor = Color.GREEN
    private var currentPercentage = 0
    private var timeBalance: List<Pair<Int, Int>> = listOf()
    private var canvasAux: Canvas? = null
    var onCallBackValue: ((value: Int) -> Unit)? = null
    var onCallBackFinish: (() -> Unit)? = null

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

    private val finishPaintPoint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = parentArcColor
        strokeWidth = 40f
        strokeCap = Paint.Cap.SQUARE
    }

    /**
     * The onDraw() method creates a Canvas for creating the balance drawing.
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setSpace()
        canvasAux = canvas
        canvasAux?.let {
            drawBackgroundArc(it)
            drawBall(it)
            drawInnerArc(it)
        }
    }

    /**
     * Draw white arc in background.
     */
    private fun drawBackgroundArc(canvas: Canvas) {
        canvas.drawArc(ovalSpace, START, END, false, parentArcPaint)
    }

    /**
     * Draw magenta arc with END accordingly the percent.
     */
    private fun drawInnerArc(canvas: Canvas) {
        val percentageToFill = getCurrentPercentageToFill()
        canvas.drawArc(ovalSpace, START, percentageToFill, false, fillArcPaint)
    }

    /**
     * Draw a small Ball at arc positioned in the point expected.
     */
    private fun drawBall(canvas: Canvas) {
        val value = getFinishPercentageToFill(timeBalance.last().second)
        canvas.drawArc(ovalSpace, value + START, 1f, false, finishPaintPoint)
    }

    /**
     * Get current percent to fill based on the variable 'currentPercentage'.
     */
    private fun getCurrentPercentageToFill() =
        (ARC_FULL_ROTATION_DEGREE * (currentPercentage / PERCENTAGE_DIVIDER)).toFloat()

    /**
     * Get the percentage for the final mate reference on the arc.
     */
    private fun getFinishPercentageToFill(value: Int) =
        ((value * PERCENTAGE_DIVIDER) / ARC_FULL_ROTATION_DEGREE).toFloat()

    /**
     * Create a invisible space for draw.
     */
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

    /**
     * Function to generate balance arc animation.
     */
    fun animateProgress(index: Int = 0, initialValue: Float = 0f) {
        restartColorToMagenta(index)
        val finalValue = timeBalance[index].second.valueToPercente()
        val valuesHolder = PropertyValuesHolder.ofFloat(
            PERCENTAGE_VALUE_HOLDER,
            initialValue,
            finalValue
        )
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
                    animateProgress(index.plus(1), finalValue)
                } else {
                    whenFinishAnimation()
                }
            }
        }
        animator.start()
    }

    /**
     * When finish animation change arc's color to GREEN and call 'onCallBackFinish'.
     */
    private fun whenFinishAnimation() {
        fillArcPaint.color = finishArcColor
        canvasAux?.let { drawInnerArc(it) }
        onCallBackFinish?.invoke()
    }

    /**
     * Reset color to MAGENTA if index is zero.
     */
    private fun restartColorToMagenta(index: Int) {
        if (index == 0) {
            fillArcPaint.color = fillArcColor
            canvasAux?.let { drawInnerArc(it) }
        }
    }

    /**
     * Set data for balance animation.
     */
    fun saveDataStream(dataStream: List<Pair<Int, Int>>) {
        timeBalance = dataStream
    }

    companion object {
        const val START: Float = 130f
        const val END: Float = 280f
        const val PERCENTAGE_VALUE_HOLDER = "percentage"
        const val ARC_FULL_ROTATION_DEGREE = 280
        const val PERCENTAGE_DIVIDER = 100.0
    }
}