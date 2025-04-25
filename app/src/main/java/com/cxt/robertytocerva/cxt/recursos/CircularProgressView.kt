package com.cxt.robertytocerva.cxt.recursos

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.cxt.robertytocerva.cxt.R

class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    var progreso: Float = 0.7f // Valor destino (70%)
        set(value) {
            animarProgreso(field, value)
            field = value
        }

    var max: Int = 10

    private var progresoAnimado: Float = 0f

    private val paintFondo = Paint().apply {
        color = ContextCompat.getColor(context,R.color.btnSecundario)
        style = Paint.Style.STROKE
        strokeWidth = 20f
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
    }

    private val paintProgreso = Paint().apply {
        color = ContextCompat.getColor(context, R.color.btnPrincipal)
        style = Paint.Style.STROKE
        strokeWidth = 20f
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
    }

    private val paintTexto = Paint().apply {
        color = ContextCompat.getColor(context, R.color.txtPrincipal)
        textSize = 60f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
        typeface = ResourcesCompat.getFont(context, R.font.principal_font)
        setShadowLayer(4f, 2f, 2f, ContextCompat.getColor(context, R.color.sombras))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2f
        val cy = height / 2f
        val radius = (minOf(width, height) / 2f) - 30f

        val rect = RectF(
            cx - radius,
            cy - radius,
            cx + radius,
            cy + radius
        )

        canvas.drawArc(rect, -90f, 360f, false, paintFondo)
        canvas.drawArc(rect, -90f, progresoAnimado * 360f, false, paintProgreso)

        canvas.drawText("${(progresoAnimado * max).toInt()}/$max", cx, cy, paintTexto)

        paintTexto.textSize = 36f
        canvas.drawText("Act. Semanal", cx, cy + 50f, paintTexto)
    }

    private fun animarProgreso(inicial: Float, final: Float) {
        val animator = ValueAnimator.ofFloat(inicial, final)
        animator.duration = 800
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener {
            progresoAnimado = it.animatedValue as Float
            invalidate()
        }
        animator.start()
    }
}

