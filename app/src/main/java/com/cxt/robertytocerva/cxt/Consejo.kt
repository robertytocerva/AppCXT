package com.cxt.robertytocerva.cxt

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class Consejo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_consejo)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_include)

        // Marca como seleccionada la opción actual
        bottomNav.selectedItemId = R.id.nav_consejos

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_consejos -> true // Ya estamos aquí
                R.id.nav_inicio -> {
                    startActivity(Intent(this, Home::class.java))
                    //overridePendingTransition(0, 0) // Sin animación de transición
                    true
                }

                else -> false
            }
        }

    }
}