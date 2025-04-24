package com.cxt.robertytocerva.cxt

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistroNino : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_nino)

        //Lista desplegable
        val spnCondicionNino = findViewById<Spinner>(R.id.spnCondicionNino)
        val listCondicionNino = listOf(
            getString(R.string.l1),
            getString(R.string.l2),
            getString(R.string.l3),
            getString(R.string.l4)
        )
        val adapter = ArrayAdapter(
            this,  // o requireContext() si estás en un Fragment
            R.layout.item_spinner,listCondicionNino
        )
        adapter.setDropDownViewResource(R.layout.item_spinner)
        spnCondicionNino.adapter = adapter
        //Fin lista desplegable

        val etNameRegistroNino = findViewById<EditText>(R.id.etNameRegistroNino)
        val etApellidoRegistroNino = findViewById<EditText>(R.id.etApellidoRegistroNino)
        val etEdadRegistroNino = findViewById<EditText>(R.id.etEdadRegistroNino)
        val btnRegistrarNino = findViewById<Button>(R.id.btnRegistrarNino)
        val btnCancelarNino = findViewById<Button>(R.id.btnCancelarNino)

        //listeners
        etNameRegistroNino.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                etNameRegistroNino.text.clear()
            }
        }
        etApellidoRegistroNino.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                etApellidoRegistroNino.text.clear()
        }
        }
        etEdadRegistroNino.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                etEdadRegistroNino.text.clear()
            }

        }
        btnRegistrarNino.setOnClickListener {
            startActivity(Intent(this, Home::class.java))
            finish()
        }
        btnCancelarNino.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
        //fin listeners

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