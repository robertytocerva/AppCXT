package com.cxt.robertytocerva.cxt

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
            //pendiente de implementar
        }
        btnCancelarNino.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
        //fin listeners


    }
}