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
import com.cxt.robertytocerva.cxt.api.NameNinoRequest
import com.cxt.robertytocerva.cxt.api.ProgresoHomeRequest
import com.cxt.robertytocerva.cxt.api.RetrofitClient
import com.cxt.robertytocerva.cxt.api.SesionRequest
import com.cxt.robertytocerva.cxt.recursos.CircularProgressView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

 class Home : AppCompatActivity() {
     var porcentajeProgreso = 0.0f
     var progreso =0.0f
     private lateinit var tvNombreJugadorCV: TextView
     private lateinit var tvJuegoCV: TextView
     private lateinit var tvTiempoJuegoCV: TextView
     private lateinit var tvProgresoCV: TextView
     private lateinit var tvMotivation: TextView
     private lateinit var tvMotivationAutor: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        gradientAnimation()
        tvNombreJugadorCV = findViewById(R.id.tvNombreJugadorCV)
        tvJuegoCV = findViewById(R.id.tvJuegoCV)
        tvTiempoJuegoCV = findViewById(R.id.tvTiempoJuegoCV)
        tvProgresoCV = findViewById(R.id.tvProgresoCV)
        tvMotivation = findViewById(R.id.tvMotivation)
        tvMotivationAutor = findViewById(R.id.tvMotivationAutor)
        obtenerProgresoHome(Globales.correo_electronico)
        obtenerNombreNino(Globales.correo_electronico)
        obtenerFrase()
        obtenerUltimaSesion(Globales.correo_electronico)

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
                R.id.nav_progreso -> {
                    startActivity(Intent(this, Progreso::class.java))
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
                    finish()
                    true
                }
                else -> false
            }
        }//----------Fin menu------------
        //----------Inicio indicador------------
//        val indicador = findViewById<CircularProgressView>(R.id.indicador)
//        indicador.progreso = porcentajeProgreso
//        indicador.max = 10
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

     private fun obtenerFrase() {
         CoroutineScope(Dispatchers.IO).launch {
             try {
                 val frase = RetrofitClient.apiService.obtenerFrase()
                 runOnUiThread {
                     tvMotivation.text = "\"${frase.frase}\""
                     tvMotivationAutor.text = "- ${frase.autor}"
                 }
             } catch (e: Exception) {
                 runOnUiThread {
                     Toast.makeText(this@Home, "Error al obtener la frase", Toast.LENGTH_SHORT).show()
                 }
             }
         }
     }

     private fun obtenerUltimaSesion(correo: String) {
         CoroutineScope(Dispatchers.IO).launch {
             try {
                 val sesion = RetrofitClient.apiService.obtenerUltimaSesion(SesionRequest(correo))
                 runOnUiThread {
                     //tvNombreJugadorCV.text = Globales.nombre_nino falta obtener el nombre del niño
                     tvJuegoCV.text = "Juego: ${sesion.nombre_juego.toString()}"
                     tvTiempoJuegoCV.text = "Fecha: ${sesion.fecha_de_sesion.substring(0,10)}"
                     tvProgresoCV.text = "Puntos: ${sesion.puntos_por_juego.toString()}"
                 }
             } catch (e: Exception) {
                 runOnUiThread {
                     Toast.makeText(this@Home, "Error al obtener sesión", Toast.LENGTH_SHORT).show()
                 }
             }
         }
     }

     private fun obtenerNombreNino(correo: String) {
         CoroutineScope(Dispatchers.IO).launch {
             try {
                 val nameNino = RetrofitClient.apiService.obtenerNombreNino(NameNinoRequest(correo))
                 runOnUiThread {
                     tvNombreJugadorCV.text = nameNino.nombre
                     Globales.nombre_nino = nameNino.nombre
                 }
             } catch (e: Exception) {
                 runOnUiThread {
                     Toast.makeText(this@Home, "Error al obtener nombre", Toast.LENGTH_SHORT).show()
                 }
             }
         }
     }
     private fun obtenerProgresoHome(correo: String)  {

         CoroutineScope(Dispatchers.IO).launch {
             try {
                 val progresoHome =
                     RetrofitClient.apiService.obtenerProgresoHome(ProgresoHomeRequest(correo))
                 runOnUiThread {
                     progreso = progresoHome.niveles_de_progreso.toFloat()/10.0f
                     porcentajeProgreso = progreso
                     val indicador = findViewById<CircularProgressView>(R.id.indicador)
                     indicador.max = 10
                     indicador.progreso = porcentajeProgreso
                     //Toast.makeText(this@Home, progreso.toString(), Toast.LENGTH_SHORT).show()
                 }
             }catch (e: Exception) {
                 runOnUiThread {
                     Toast.makeText(this@Home, "Error al obtener progreso", Toast.LENGTH_SHORT).show()
                 }
             }
         }
     }
}