package com.cxt.robertytocerva.cxt

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class Ajustes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajustes)
        gradientAnimation()

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        //-------Inicio Menu-------
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_include)
        // Marca como seleccionada la opción actual
        bottomNav.selectedItemId = R.id.nav_ajustes

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_ajustes -> true // Ya estamos aquí
                R.id.nav_inicio -> {
                    startActivity(Intent(this, Home::class.java))
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
                    finish()
                    true
                }
                R.id.nav_consejos -> {
                    startActivity(Intent(this, Consejo::class.java))
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
                    finish()
                    true
                }
                R.id.nav_juego -> {
                    startActivity(Intent(this, Juego::class.java))
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
                    finish()
                    true
                }

                else -> false
            }
        }
        //-------Fin Menu-------

        btnGuardar.setOnClickListener {
            // Lógica para guardar los cambios
            Toast.makeText(this, R.string.toastGuardar, Toast.LENGTH_SHORT).show()
        }
        btnCerrarSesion.setOnClickListener {
            // Lógica para cerrar sesión
            startActivity(Intent(this, Login::class.java))
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
}