package com.cxt.robertytocerva.cxt.secundarias

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cxt.robertytocerva.cxt.Consejo
import com.cxt.robertytocerva.cxt.R

class DetalleConsejoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_consejo)
        gradientAnimation()

        val titulo = intent.getStringExtra("titulo") ?: "Sin título"
        val contenido = intent.getStringExtra("contenido") ?: "Sin contenido"

        findViewById<TextView>(R.id.tvTitleConseoCompleto).text = titulo
        findViewById<TextView>(R.id.tvConseoCompleto).text = contenido

        val flecha = findViewById<View>(R.id.vwArrow)
        flecha.setOnClickListener {
            startActivity(Intent(this, Consejo::class.java))
            finish()
        }

    }

    fun gradientAnimation() {
        val layout = findViewById<ConstraintLayout>(R.id.main)
        val transition = layout.background as? TransitionDrawable

        var forward = true
        val handler = Handler(Looper.getMainLooper())
        val duration = 3000L // duración de cada transición

        val runnable = object : java.lang.Runnable {
            override fun run() {
                if (forward) {
                    transition?.startTransition(duration.toInt())
                } else {
                    transition?.reverseTransition(duration.toInt())
                }
                forward = !forward
                handler.postDelayed(this, duration)
            }
        }

        handler.post(runnable)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, R.anim.scale_exit)
    }
}