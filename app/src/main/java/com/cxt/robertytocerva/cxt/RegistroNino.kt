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
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cxt.robertytocerva.cxt.api.NinoRegistro
import com.cxt.robertytocerva.cxt.api.RetrofitClient
import com.cxt.robertytocerva.cxt.api.TutorEnvio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistroNino : AppCompatActivity() {
    var idDiscapacidad: Int = 0
    private lateinit var etNameRegistroNino: EditText
    private lateinit var etApellidoRegistroNino: EditText
    private lateinit var etEdadRegistroNino: EditText
    private lateinit var btnRegistrarNino: Button
    private lateinit var btnCancelarNino: Button
    private lateinit var etActWeek: EditText
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

        spnCondicionNino.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                when (position) {
                    0 -> idDiscapacidad = 1
                    1 -> idDiscapacidad = 2
                    3 -> idDiscapacidad = 3
                    2 -> idDiscapacidad = 4
                }
                Toast.makeText(this@RegistroNino, "ID discapacidad: $idDiscapacidad", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {
                // No hacer nada si no selecciona
            }
        }

        //Fin lista desplegable

        etNameRegistroNino = findViewById<EditText>(R.id.etNameRegistroNino)
        etApellidoRegistroNino = findViewById<EditText>(R.id.etApellidoRegistroNino)
        etEdadRegistroNino = findViewById<EditText>(R.id.etEdadRegistroNino)
        btnRegistrarNino = findViewById<Button>(R.id.btnRegistrarNino)
        btnCancelarNino = findViewById<Button>(R.id.btnCancelarNino)
        //etActWeek = findViewById<EditText>(R.id.etActWeek)

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
            //Globales.act_week = etActWeek.text.toString().toInt()
            registrarTutor()
            registrarNino()

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

    //Registro Tutor
    private fun registrarTutor() {
        val correo = Globales.correo_electronico
        val nombre = Globales.nombre_tutor
        val apellido = Globales.apellido_tutor
        val telefono = Globales.telefono_tutor
        val contrasena = Globales.contrasena_tutor
        if (correo.isNotEmpty() && nombre.isNotEmpty() && apellido.isNotEmpty() && telefono.isNotEmpty() && contrasena.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val nuevoTutor = TutorEnvio(correo, nombre, apellido, telefono, contrasena)
                    val response = RetrofitClient.apiService.agregarTutor(nuevoTutor)
                    runOnUiThread {
                        if (response.isSuccessful) {
                            //Toast.makeText(this@RegistroNino, "Tutor registrado exitosamente", Toast.LENGTH_SHORT).show()
                            //finish()
                        } else {
                            Toast.makeText(this@RegistroNino, "Error al registrar", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@RegistroNino, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registrarNino() {
        val nombre = etNameRegistroNino.text.toString()
        val apellido = etApellidoRegistroNino.text.toString()
        val edad = etEdadRegistroNino.text.toString().toInt()
        val idDiscapacidad = idDiscapacidad
        val correo = Globales.correo_electronico

        if (nombre.isNotEmpty() && apellido.isNotEmpty() && edad > 0 && idDiscapacidad > 0 && correo.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val nuevoNino = NinoRegistro(nombre, apellido, edad, idDiscapacidad, correo)
                    val response = RetrofitClient.apiService.agregarNino(nuevoNino)
                    runOnUiThread {
                        if (response.isSuccessful) {
                            Globales.nombre_nino = nombre
                            Toast.makeText(this@RegistroNino, "Registro exitoso", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@RegistroNino, Home::class.java)
                            startActivity(intent)
                            finish()
                        }else {
                            Toast.makeText(this@RegistroNino, "Error al registrar", Toast.LENGTH_SHORT).show()
                        }
                    }
                }catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@RegistroNino, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}