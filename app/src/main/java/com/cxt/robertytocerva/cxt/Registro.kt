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
    private lateinit var etNameRegistro: EditText
    private lateinit var etApellidoRegistro: EditText
    private lateinit var etCorreoRegistro: EditText
    private lateinit var etTelefonoRegistro: EditText
    private lateinit var etContrasenaRegistro: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnCancelar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        enableEdgeToEdge()

         etNameRegistro = findViewById<EditText>(R.id.etNameRegistro)
         etApellidoRegistro = findViewById<EditText>(R.id.etApellidoRegistro)
         etCorreoRegistro = findViewById<EditText>(R.id.etCorreoRegistro)
         etTelefonoRegistro = findViewById<EditText>(R.id.etTelefonoRegistro)
         etContrasenaRegistro = findViewById<EditText>(R.id.etContrasenaRegistro)
         btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
         btnCancelar = findViewById<Button>(R.id.btnCancelar)
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
                etContrasenaRegistro.text.clear()
            }

        }

        btnRegistrar.setOnClickListener {
            Globales.nombre_tutor = etNameRegistro.text.toString()
            Globales.apellido_tutor = etApellidoRegistro.text.toString()
            Globales.correo_electronico = etCorreoRegistro.text.toString()
            Globales.telefono_tutor = etTelefonoRegistro.text.toString()
            Globales.contrasena_tutor = etContrasenaRegistro.text.toString()
            startActivity(Intent(this, RegistroNino::class.java))
            finish()
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