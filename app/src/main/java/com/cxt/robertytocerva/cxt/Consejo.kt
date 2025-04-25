package com.cxt.robertytocerva.cxt

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cxt.robertytocerva.cxt.secundarias.DetalleConsejoActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

class Consejo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_consejo)

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

                else -> false
            }
        }
        //-------Fin Menu-------

        val card1 = findViewById<MaterialCardView>(R.id.cardViewConsejo1)
        card1.setOnClickListener {
            val intent = Intent(this, DetalleConsejoActivity::class.java)
            intent.putExtra("titulo", "Consejo 1")
            intent.putExtra("contenido", ContextCompat.getString(this, R.string.tvConsejo))

            val options = ActivityOptionsCompat.makeScaleUpAnimation(
                card1, // Vista origen de la animación
                0, 0,  // Coordenadas del centro
                card1.width,
                card1.height
            )
            startActivity(intent,options.toBundle())
        }

        val card2 = findViewById<MaterialCardView>(R.id.cardViewConsejo2)
        card2.setOnClickListener {
            val intent = Intent(this, DetalleConsejoActivity::class.java)
            intent.putExtra("titulo", "Consejo 2")
            intent.putExtra("contenido", ContextCompat.getString(this, R.string.tvConsejo))
            val options = ActivityOptionsCompat.makeScaleUpAnimation(
                card1, // Vista origen de la animación
                0, 0,  // Coordenadas del centro
                card1.width,
                card1.height
            )
            startActivity(intent,options.toBundle())
        }
        val card3 = findViewById<MaterialCardView>(R.id.cardViewConsejo3)
        card3.setOnClickListener {
            val intent = Intent(this, DetalleConsejoActivity::class.java)
            intent.putExtra("titulo", "Consejo 3")
            intent.putExtra("contenido", ContextCompat.getString(this, R.string.tvConsejo))
            val options = ActivityOptionsCompat.makeScaleUpAnimation(
                card1, // Vista origen de la animación
                0, 0,  // Coordenadas del centro
                card1.width,
                card1.height
            )
            startActivity(intent,options.toBundle())
        }


    }
}