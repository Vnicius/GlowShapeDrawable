package io.github.vnicius.glowshapedrawable

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import java.util.*

class GlowShapeDrawable : LayerDrawable(arrayOf(ShapeDrawable())) {
    private val defaultColor = Color.TRANSPARENT
    private val defaultColorList =
        ColorStateList(arrayOf(intArrayOf(android.R.attr.defaultValue)), intArrayOf(defaultColor))
    private val drawable = getDrawable(0) as ShapeDrawable

    val paint: Paint = drawable.paint
    var backgroundColor: Int = Color.TRANSPARENT
    var backgroundColorList: ColorStateList = defaultColorList
    var glow: Glow = Glow(defaultColorList, size = 0)
        set(value) {
            field = value

            setLayerInset(0, glow.size, glow.size, glow.size, glow.size)
        }
    var cornerRadius: Float = 0f
        set(value) {
            val outerRadius = FloatArray(8)
            Arrays.fill(outerRadius, value)
            drawable.shape = RoundRectShape(outerRadius, null, null)

            field = value
        }

    override fun isStateful(): Boolean = true

    override fun onStateChange(state: IntArray?): Boolean {
        val glowColorStateList = glow.colorList ?: defaultColorList
        val backgroundColor = if (backgroundColorList != defaultColorList)
            backgroundColorList.getColorForState(state, backgroundColorList.defaultColor)
        else
            this.backgroundColor
        val glowColor = if (glowColorStateList != defaultColorList)
            glowColorStateList.getColorForState(state, glowColorStateList.defaultColor)
        else
            glow.color ?: defaultColor

        paint.apply {
            color = backgroundColor
            setShadowLayer(glow.size.toFloat(), 0f, 0f, glowColor)
        }

        return true
    }
}