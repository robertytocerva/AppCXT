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
import com.cxt.robertytocerva.cxt.api.DiscapacidadRequest
import com.cxt.robertytocerva.cxt.api.RetrofitClient
import com.cxt.robertytocerva.cxt.secundarias.DetalleConsejoActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Consejo : AppCompatActivity() {
    private var recomendacionRes1 = ""
    private var recomendacionRes2 = ""
    private var recomendacionRes3 = ""
    private var recomendacionCompleta1 = ""
    private var recomendacionCompleta2 = ""
    private var recomendacionCompleta3 = ""
    private var autor1 = ""
    private var autor2 = ""
    private var autor3 = ""
    private var titulo1 = ""
    private var titulo2 = ""
    private var titulo3 = ""

    private lateinit var tvTitleConseoCV1: TextView
    private lateinit var tvConseoCV1: TextView
    private lateinit var tvTitleConseoCV2: TextView
    private lateinit var tvConseoCV2: TextView
    private lateinit var tvTitleConseoCV3: TextView
    private lateinit var tvConseoCV3: TextView
    private lateinit var card1: MaterialCardView
    private lateinit var card2: MaterialCardView
    private lateinit var card3: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_consejo)
        gradientAnimation()
        cargarRecomendaciones(Globales.id_discapacidad)
        //-------Inicio Menu-------
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_include)
        // Marca como seleccionada la opción actual
        bottomNav.selectedItemId = R.id.nav_consejos

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_consejos -> true // Ya estamos aquí
                R.id.nav_inicio -> {
                    startActivity(Intent(this, Home::class.java))
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
        }
        //-------Fin Menu-------

        card1 = findViewById<MaterialCardView>(R.id.cardViewConsejo1)
        card1.setOnClickListener {
            abrirDetalleConsejo(titulo1, recomendacionCompleta1, autor1)
        }

        card2 = findViewById<MaterialCardView>(R.id.cardViewConsejo2)
        card2.setOnClickListener {
            abrirDetalleConsejo(titulo2, recomendacionCompleta2, autor2)
        }
        card3 = findViewById<MaterialCardView>(R.id.cardViewConsejo3)
        card3.setOnClickListener {
            abrirDetalleConsejo(titulo3, recomendacionCompleta3, autor3)
        }

    }

    private fun cargarRecomendaciones(idDiscapacidad: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val recomendaciones = RetrofitClient.apiService.obtenerRecomendaciones(
                    DiscapacidadRequest(idDiscapacidad)
                )
                runOnUiThread {
                    if(recomendaciones.size>=3){
                        val recomendacion1 = recomendaciones[0]
                        val recomendacion2 = recomendaciones[1]
                        val recomendacion3 = recomendaciones[2]

                        autor1 = recomendacion1.autor
                        autor2 = recomendacion2.autor
                        autor3 = recomendacion3.autor
                        titulo1 = recomendacion1.titulo
                        titulo2 = recomendacion2.titulo
                        titulo3 = recomendacion3.titulo
                        recomendacionCompleta1 = recomendacion1.recomendacion_completa
                        recomendacionCompleta2 = recomendacion2.recomendacion_completa
                        recomendacionCompleta3 = recomendacion3.recomendacion_completa
                        recomendacionRes1 = recomendacion1.recomendacion
                        recomendacionRes2 = recomendacion2.recomendacion
                        recomendacionRes3 = recomendacion3.recomendacion

                        findViewById<TextView>(R.id.tvTitleConseoCV1).text = titulo1
                        findViewById<TextView>(R.id.tvConseoCV1).text = recomendacionRes1
                        findViewById<TextView>(R.id.tvTitleConseoCV2).text = titulo2
                        findViewById<TextView>(R.id.tvConseoCV2).text = recomendacionRes2
                        findViewById<TextView>(R.id.tvTitleConseoCV3).text = titulo3
                        findViewById<TextView>(R.id.tvConseoCV3).text = recomendacionRes3


                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@Consejo, "Error al obtener recomendaciones", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun abrirDetalleConsejo(titulo: String, contenido: String, autor: String) {
        val intent = Intent(this, DetalleConsejoActivity::class.java)
        intent.putExtra("titulo", titulo)
        intent.putExtra("recomdacion_completa", contenido)
        intent.putExtra("autor", autor)
        val options = ActivityOptionsCompat.makeScaleUpAnimation(
            card1, // Vista origen de la animación
            0, 0,  // Coordenadas del centro
            card1.width,
            card1.height
        )
        startActivity(intent,options.toBundle())
        finish()
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