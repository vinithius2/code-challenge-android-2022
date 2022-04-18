package com.example.codechallengedrop.ui.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.codechallengedrop.databinding.ColorAbvStatusBallBinding


class ColorAbvStatusBall(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private var ABV: Double = 0.0
        set(value) {
            field = value
            setStatusAbv(value)
        }

    private val binding =
        ColorAbvStatusBallBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Level ABV with colors
     * 0.0 between 0.5% - Blue light.
     * 0.5% between 4.0% - Green.
     * 4.0% between 5% - Yellow
     * 5% between 6.5% - Orange
     * Over 6.5% - Red.
     */
    private fun setStatusAbv(abv_input: Double) {
        when {
            abv_input in ZERO..VERY_LOW -> { // Blue light
                setChangeColor("#00FFE1")
            }
            abv_input in VERY_LOW..LOW -> { // Green
                setChangeColor("#3FEC00")
            }
            abv_input in LOW..MEDIUM -> { // Yellow
                setChangeColor("#FFEB3B")
            }
            abv_input in MEDIUM..HIGH -> { // Orange
                setChangeColor("#FF5722")
            }
            abv_input > HIGH -> { // Red
                setChangeColor("#FF0000")
            }
        }
    }

    private fun setChangeColor(color: String) {
        val shape = binding.imageBeer.drawable as GradientDrawable
        shape.setColor(Color.parseColor(color))
    }

    fun setData(
        abv_input: Double
    ) {
        ABV = abv_input
    }

    companion object {
        const val ZERO: Double = 0.0
        const val VERY_LOW: Double = 0.5
        const val LOW: Double = 4.0
        const val MEDIUM: Double = 5.0
        const val HIGH: Double = 6.5
    }
}
