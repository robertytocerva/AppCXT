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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cxt.robertytocerva.cxt.api.IdNinoRequest
import com.cxt.robertytocerva.cxt.api.RetrofitClient
import com.cxt.robertytocerva.cxt.api.TutorRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Progreso : AppCompatActivity() {
    private lateinit var tvNombreJugadorProgresoCV: TextView
    private lateinit var tvJuegoProgresoCV: TextView
    private lateinit var tvPuntosProgresoCV: TextView
    private lateinit var tvProgresoProgresoCV: TextView
    private lateinit var tvBestSesion: TextView
    private lateinit var tvGameScore: TextView
    private lateinit var tvFechaProgresoCV: TextView
    private lateinit var tvNivelCV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_progreso)
        gradientAnimation()
        cargarMejorSesion(Globales.correo_electronico)


        //----------Inicio menu------------
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_include)

        // Marca como seleccionada la opción actual
        bottomNav.selectedItemId = R.id.nav_progreso

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_progreso -> true // Ya estamos aquí
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
                R.id.nav_inicio -> {
                    startActivity(Intent(this, Home::class.java))
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
                    finish()
                    true
                }
                else -> false
            }
        }//----------Fin menu------------
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

    private fun cargarMejorSesion(correo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val respuesta = RetrofitClient.apiService.obtenerMejorSesion(TutorRequest(correo))
                runOnUiThread {
                    Globales.id_nino = respuesta.id_nino
                    Toast.makeText(this@Progreso, "id nino: ${Globales.id_nino}", Toast.LENGTH_SHORT).show()
                    findViewById<TextView>(R.id.tvNombreJugadorProgresoCV).text = Globales.nombre_nino
                    findViewById<TextView>(R.id.tvJuegoProgresoCV).text = "Juego: ${respuesta.nombre_juego}"
                    findViewById<TextView>(R.id.tvProgresoProgresoCV).text = "Progreso: ${respuesta.nivel_actual}"
                    findViewById<TextView>(R.id.tvPuntosProgresoCV).text = "Puntos por sesión: ${respuesta.puntos_por_juego}"
                    cargarProgreso(respuesta.id_nino)
                }
            } catch (e: Exception) {
                runOnUiThread {

                    Toast.makeText(this@Progreso, "No se pudo obtener mejor sesión", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun cargarProgreso(idNino: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val progreso = RetrofitClient.apiService.obtenerProgresoPorNino(IdNinoRequest(idNino))
                runOnUiThread {
                    findViewById<TextView>(R.id.tvNivelCV).text = progreso.niveles_de_progreso.toString()
                    findViewById<TextView>(R.id.tvFechaProgresoCV).text = progreso.fecha.substring(0,10)
                    findViewById<TextView>(R.id.tvGameScore).text = "Game Score: ${progreso.puntos_totales}"
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@Progreso, "Error al obtener progreso", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}