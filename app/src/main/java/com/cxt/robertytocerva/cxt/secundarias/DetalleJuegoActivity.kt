package com.cxt.robertytocerva.cxt.secundarias

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cxt.robertytocerva.cxt.Consejo
import com.cxt.robertytocerva.cxt.Juego
import com.cxt.robertytocerva.cxt.R

class DetalleJuegoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_juego)
        val titulo = intent.getStringExtra("titulo") ?: "Sin título"
        val contenido = intent.getStringExtra("contenido") ?: "Sin contenido"

        findViewById<TextView>(R.id.tvTitleJuegoCompleto).text = titulo
        findViewById<TextView>(R.id.tvJuegoCompleto).text = contenido


        val flecha = findViewById<View>(R.id.vwArrowJuego)
        flecha.setOnClickListener {
            startActivity(Intent(this, Juego::class.java))
            finish()
        }
    }
}