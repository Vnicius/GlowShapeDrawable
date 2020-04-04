package io.github.vnicius.glowshapedrawable

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import io.github.vnicius.glowshapedrawable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        setupTrafficLight()
        setupLightSaber()
        setupButton()
    }

    private fun setupTrafficLight() {
        val redLightDrawable = GlowShapeDrawable().apply {
            backgroundColorList = ContextCompat.getColorStateList(baseContext, R.color.red_light)!!
            glow = Glow(
                ContextCompat.getColorStateList(baseContext, R.color.red_light_glow)!!,
                size = baseContext.resources.getDimension(R.dimen.glow_size).toInt()
            )
            cornerRadius = baseContext.resources.getDimension(R.dimen.corner_radius)
        }
        val yellowLightDrawable = GlowShapeDrawable().apply {
            backgroundColorList =
                ContextCompat.getColorStateList(baseContext, R.color.yellow_light)!!
            glow = Glow(
                ContextCompat.getColorStateList(baseContext, R.color.yellow_light_glow)!!,
                size = baseContext.resources.getDimension(R.dimen.glow_size).toInt()
            )
            cornerRadius = baseContext.resources.getDimension(R.dimen.corner_radius)
        }
        val greenLightDrawable = GlowShapeDrawable().apply {
            backgroundColorList =
                ContextCompat.getColorStateList(baseContext, R.color.green_light)!!
            glow = Glow(
                ContextCompat.getColorStateList(baseContext, R.color.green_light_glow)!!,
                size = baseContext.resources.getDimension(R.dimen.glow_size).toInt()
            )
            cornerRadius = baseContext.resources.getDimension(R.dimen.corner_radius)
        }

        viewBinding.trafficLight.children.forEach { child ->
            val glowShapeDrawable = when (child.id) {
                R.id.red_light -> redLightDrawable
                R.id.yellow_light -> yellowLightDrawable
                else -> greenLightDrawable
            }

            child.apply {
                setLayerType(View.LAYER_TYPE_SOFTWARE, glowShapeDrawable.paint)
                background = glowShapeDrawable
                setOnClickListener {
                    switchLight(it)
                }
            }
        }
    }

    private fun switchLight(view: View) {
        if (view.isSelected) {
            view.isSelected = false
        } else {
            viewBinding.trafficLight.children.forEach { child ->
                child.isSelected = child.id == view.id
            }
        }
    }

    private fun setupLightSaber() {
        val lightSaberDrawable = GlowShapeDrawable().apply {
            backgroundColorList = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.defaultValue)
                ), intArrayOf(Color.CYAN)
            )
            glow = Glow(
                ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.defaultValue)
                    ), intArrayOf(Color.CYAN)
                ),
                size = baseContext.resources.getDimension(R.dimen.glow_size).toInt()
            )
            cornerRadius = baseContext.resources.getDimension(R.dimen.corner_radius)
        }

        viewBinding.lightSaber.apply {
            setLayerType(View.LAYER_TYPE_SOFTWARE, lightSaberDrawable.paint)
            background = lightSaberDrawable
        }

        viewBinding.hilt.apply {
            setOnClickListener {
                ValueAnimator.ofInt(
                    0,
                    baseContext.resources.getDimension(R.dimen.light_saber_size).toInt()
                )
                    .apply {
                        duration = 1000L
                        addUpdateListener {
                            val lp = viewBinding.lightSaber.layoutParams
                            lp.width = it.animatedValue as Int
                            viewBinding.lightSaber.layoutParams = lp
                            viewBinding.lightSaber.requestLayout()
                        }
                    }.start()
            }
        }
    }

    private fun setupButton() {
        val backgroundDrawable = GlowShapeDrawable().apply {
            backgroundColor = ResourcesCompat.getColor(baseContext.resources, R.color.colorPrimary, null)
            glow = Glow(
                ColorStateList(
                    arrayOf(intArrayOf(android.R.attr.defaultValue)),
                    intArrayOf(Color.MAGENTA)
                ), size =  baseContext.resources.getDimension(R.dimen.glow_size).toInt()
            )
        }

        viewBinding.button.apply {
            setLayerType(View.LAYER_TYPE_SOFTWARE, backgroundDrawable.paint)
            background = backgroundDrawable
        }
    }
}
