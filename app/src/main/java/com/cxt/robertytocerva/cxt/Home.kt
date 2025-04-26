 package com.cxt.robertytocerva.cxt

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.cxt.robertytocerva.cxt.recursos.CircularProgressView
import com.google.android.material.bottomnavigation.BottomNavigationView

 class Home : AppCompatActivity() {
     val porcentajeProgreso = 0.7f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        gradientAnimation()
        //----------Inicio menu------------
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_include)

        // Marca como seleccionada la opción actual
        bottomNav.selectedItemId = R.id.nav_inicio

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> true // Ya estamos aquí
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
                R.id.nav_ajustes -> {
                    startActivity(Intent(this, Ajustes::class.java))
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
                    finish()
                    true
                }

                else -> false
            }
        }//----------Fin menu------------
        //----------Inicio indicador------------
        val indicador = findViewById<CircularProgressView>(R.id.indicador)
        indicador.progreso = porcentajeProgreso
        indicador.max = 10
        //----------Fin indicador------------

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