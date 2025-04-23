package com.cxt.robertytocerva.cxt

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Registro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        enableEdgeToEdge()

         val etNameRegistro = findViewById<EditText>(R.id.etNameRegistro)
         val etApellidoRegistro = findViewById<EditText>(R.id.etApellidoRegistro)
         val etCorreoRegistro = findViewById<EditText>(R.id.etCorreoRegistro)
         val etTelefonoRegistro = findViewById<EditText>(R.id.etTelefonoRegistro)
         val etContrasenaRegistro = findViewById<EditText>(R.id.etContrasenaRegistro)
         val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
         val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        etNameRegistro.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                etNameRegistro.text.clear()
            }
        }
        etApellidoRegistro.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                etApellidoRegistro.text.clear()
            }
        }
        etCorreoRegistro.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                etCorreoRegistro.text.clear()
            }
        }
        etTelefonoRegistro.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                etTelefonoRegistro.text.clear()
            }
        }
        etContrasenaRegistro.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                etCorreoRegistro.text.clear()
            }

        }

        btnRegistrar.setOnClickListener {

        }
        btnCancelar.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        gradientAnimation()
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