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
        val nombreJuego = intent.getStringExtra("nombre_juego")
        val descripcionCompleta = intent.getStringExtra("descripcion_completa")

        val textNombreJuego = findViewById<TextView>(R.id.tvTitleJuegoCompleto)
        val textDescripcionCompleta = findViewById<TextView>(R.id.tvJuegoCompleto)

        textNombreJuego.text = nombreJuego
        textDescripcionCompleta.text = descripcionCompleta


        val flechaJuego = findViewById<View>(R.id.vwArrowJuego)
        flechaJuego.setOnClickListener {
            startActivity(Intent(this, Juego::class.java))
            finish()
        }
    }
}