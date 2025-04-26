package com.cxt.robertytocerva.cxt

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cxt.robertytocerva.cxt.secundarias.DetalleConsejoActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

class Juego : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_juego)
        gradientAnimation()

        //-------Inicio Menu-------
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_include)
        // Marca como seleccionada la opción actual
        bottomNav.selectedItemId = R.id.nav_juego

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_juego -> true // Ya estamos aquí
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

                else -> false
            }
        }
        //-------Fin Menu-------

        val card1 = findViewById<MaterialCardView>(R.id.cardViewJuego1)
        card1.setOnClickListener {
            val intent = Intent(this, DetalleConsejoActivity::class.java)
            intent.putExtra("titulo", "Juego 1")
            intent.putExtra("contenido", ContextCompat.getString(this, R.string.tvDescripcionJuego1))


            val options = ActivityOptionsCompat.makeScaleUpAnimation(
                card1, // Vista origen de la animación
                0, 0,  // Coordenadas del centro
                card1.width,
                card1.height
            )
            startActivity(intent,options.toBundle())
            finish()
        }

        val card2 = findViewById<MaterialCardView>(R.id.cardViewJuego2)
        card2.setOnClickListener {
            val intent = Intent(this, DetalleConsejoActivity::class.java)
            intent.putExtra("titulo", "Juego 2")
            intent.putExtra("contenido", ContextCompat.getString(this, R.string.tvDescripciinJuego2))
            val options = ActivityOptionsCompat.makeScaleUpAnimation(
                card1, // Vista origen de la animación
                0, 0,  // Coordenadas del centro
                card1.width,
                card1.height
            )
            startActivity(intent,options.toBundle())
            finish()
        }
        val card3 = findViewById<MaterialCardView>(R.id.cardViewJuego3)
        card3.setOnClickListener {
            val intent = Intent(this, DetalleConsejoActivity::class.java)
            intent.putExtra("titulo", "Juego 3")
            intent.putExtra("contenido", ContextCompat.getString(this, R.string.tvDescripcionJuego1))
            val options = ActivityOptionsCompat.makeScaleUpAnimation(
                card1, // Vista origen de la animación
                0, 0,  // Coordenadas del centro
                card1.width,
                card1.height
            )
            startActivity(intent,options.toBundle())
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