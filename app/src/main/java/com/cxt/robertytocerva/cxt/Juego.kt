package com.cxt.robertytocerva.cxt

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cxt.robertytocerva.cxt.api.RetrofitClient
import com.cxt.robertytocerva.cxt.secundarias.DetalleConsejoActivity
import com.cxt.robertytocerva.cxt.secundarias.DetalleJuegoActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Juego : AppCompatActivity() {
    private lateinit var tvTitleJuegoCV1: TextView
    private lateinit var tvTitleJuegoCV2: TextView
    private lateinit var tvTitleJuegoCV3: TextView
    private lateinit var tvJuegoCV1: TextView
    private lateinit var tvJuegoCV2: TextView
    private lateinit var tvJuegoCV3: TextView
    private lateinit var card1: MaterialCardView
    private lateinit var card2: MaterialCardView
    private lateinit var card3: MaterialCardView
    var titulo1= ""
    var contenido1 =""
    var titulo2 = ""
    var contenido2 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_juego)
        gradientAnimation()
        cargarJuegos()
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
                R.id.nav_ajustes -> {
                    startActivity(Intent(this, Ajustes::class.java))
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
                    finish()
                    true
                }
                R.id.nav_progreso -> {
                    startActivity(Intent(this, Progreso::class.java))
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
                    finish()
                    true
                }

                else -> false
            }
        }
        //-------Fin Menu-------

        card1 = findViewById<MaterialCardView>(R.id.cardViewJuego1)
        card1.setOnClickListener {
            abrirDetalleJuego(titulo1, contenido1)
        }

        card2 = findViewById<MaterialCardView>(R.id.cardViewJuego2)
        card2.setOnClickListener {
            abrirDetalleJuego(titulo2, contenido2)
        }
        card3 = findViewById<MaterialCardView>(R.id.cardViewJuego3)
        card3.setOnClickListener {
//            val intent = Intent(this, DetalleJuegoActivity::class.java)
//            intent.putExtra("titulo", "Juego 3")
//            intent.putExtra("contenido", ContextCompat.getString(this, R.string.tvDescripcionJuego1))
//            val options = ActivityOptionsCompat.makeScaleUpAnimation(
//                card1, // Vista origen de la animación
//                0, 0,  // Coordenadas del centro
//                card1.width,
//                card1.height
//            )
//            startActivity(intent,options.toBundle())
//            finish()
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

    private fun cargarJuegos() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val listaJuegos = RetrofitClient.apiService.obtenerJuegos()
                runOnUiThread {
                    if (listaJuegos.size >= 2) {
                        val juego1 = listaJuegos[0]
                        val juego2 = listaJuegos[1]

                        // Supongamos que tienes TextViews en cada CardView
                        findViewById<TextView>(R.id.tvTitleJuegoCV1).text = juego1.nombre_juego
                        findViewById<TextView>(R.id.tvJuegoCV1).text = juego1.descripcion

                        titulo1= juego1.nombre_juego
                        contenido1= juego1.descripcion_completa

                        findViewById<TextView>(R.id.tvTitleJuegoCV2).text = juego2.nombre_juego
                        findViewById<TextView>(R.id.tvJuegoCV2).text = juego2.descripcion
                        titulo2= juego2.nombre_juego
                        contenido2= juego2.descripcion_completa
                        // Puedes hacer lo mismo para el juego 3
                        findViewById<TextView>(R.id.tvTitleJuegoCV3).text = "Proximamante"
                        findViewById<TextView>(R.id.tvJuegoCV3).text = "Estamos construyendo más diversión"
                    } else {
                        Toast.makeText(this@Juego, "No hay suficientes juegos para mostrar", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@Juego, "Error al cargar juegos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun abrirDetalleJuego(nombreJuego: String, descripcionCompleta: String) {

        val intent = Intent(this, DetalleJuegoActivity::class.java)
        intent.putExtra("nombre_juego", nombreJuego)
        intent.putExtra("descripcion_completa", descripcionCompleta)
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