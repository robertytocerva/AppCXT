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
import com.cxt.robertytocerva.cxt.api.ActualizarNinoRequest
import com.cxt.robertytocerva.cxt.api.ActualizarTutorRequest
import com.cxt.robertytocerva.cxt.api.NinoRequest
import com.cxt.robertytocerva.cxt.api.RetrofitClient
import com.cxt.robertytocerva.cxt.api.TutorRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Ajustes : AppCompatActivity() {
    private lateinit var etNameTutorAjustes: EditText
    private lateinit var etApellidoTutorAjustes: EditText
    private lateinit var etCorreoAjustes: EditText
    private lateinit var etTelefonoAjustes: EditText

    private lateinit var etNameNinoAjustes: EditText
    private lateinit var etApellidoNinoAjustes: EditText
    private lateinit var etEdadNinoAjustes: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajustes)
        //--iniclaizar variables--
        etNameTutorAjustes = findViewById(R.id.etNameTutorAjustes)
        etApellidoTutorAjustes = findViewById(R.id.etApellidoTutorAjustes)
        etCorreoAjustes = findViewById(R.id.etCorreoAjustes)
        etTelefonoAjustes = findViewById(R.id.etTelefonoAjustes)

        etNameNinoAjustes = findViewById(R.id.etNameNinoAjustes)
        etApellidoNinoAjustes = findViewById(R.id.etApellidoNinoAjustes)
        etEdadNinoAjustes = findViewById(R.id.etEdadNinoAjustes)

        etCorreoAjustes.isFocusable = false
        etCorreoAjustes.isClickable = false
        //--fin iniclaizar variables--

        gradientAnimation()
        buscarTutor(Globales.correo_electronico)
        buscarNino(Globales.correo_electronico)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        //-------Inicio Menu-------
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_include)
        // Marca como seleccionada la opción actual
        bottomNav.selectedItemId = R.id.nav_ajustes

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_ajustes -> true // Ya estamos aquí
                R.id.nav_inicio -> {
                    startActivity(Intent(this, Home::class.java))
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
                    finish()
                    true
                }
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

        btnGuardar.setOnClickListener {
            // Lógica para guardar los cambios
            actualizarNino()
            actualizarTutor()
            Toast.makeText(this, R.string.toastGuardar, Toast.LENGTH_SHORT).show()
        }
        btnCerrarSesion.setOnClickListener {
            // Lógica para cerrar sesión
            startActivity(Intent(this, Login::class.java))
            finish()
        }

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

    private fun buscarTutor(correo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val tutor = RetrofitClient.apiService.buscarTutor(TutorRequest(correo))
                runOnUiThread {
                    etNameTutorAjustes.setText(tutor.nombre)
                    etApellidoTutorAjustes.setText(tutor.apellido)
                    etCorreoAjustes.setText(tutor.correo_electronico)
                    etTelefonoAjustes.setText(tutor.telefono)
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@Ajustes, "Error al obtener datos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun buscarNino(correo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val nino = RetrofitClient.apiService.buscarNino(NinoRequest(correo))
                runOnUiThread {
                    etNameNinoAjustes.setText(nino.nombre)
                    etApellidoNinoAjustes.setText(nino.apellido)
                    etEdadNinoAjustes.setText(nino.edad.toString())
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@Ajustes, "Error al obtener datos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun actualizarNino() {
        val nombre = etNameNinoAjustes.text.toString()
        val apellido = etApellidoNinoAjustes.text.toString()
        val edad = etEdadNinoAjustes.text.toString().toIntOrNull() ?: 0
        val correo = Globales.correo_electronico

        if (nombre.isNotEmpty() && apellido.isNotEmpty() && correo.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitClient.apiService.actualizarNino(
                        ActualizarNinoRequest(nombre, apellido, edad, correo)
                    )
                    runOnUiThread {
                        if (response.isSuccessful) {
                            Toast.makeText(this@Ajustes, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@Ajustes, "Error al actualizar datos", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@Ajustes, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarTutor() {
        val nombre = etNameTutorAjustes.text.toString()
        val apellido = etApellidoTutorAjustes.text.toString()
        val telefono = etTelefonoAjustes.text.toString()
        val correo = etCorreoAjustes.text.toString()

        if (nombre.isNotEmpty() && apellido.isNotEmpty() && telefono.isNotEmpty() && correo.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitClient.apiService.actualizarTutor(
                        ActualizarTutorRequest(nombre, apellido, telefono, correo)
                    )
                    runOnUiThread {
                        if (response.isSuccessful) {
                            Toast.makeText(this@Ajustes, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@Ajustes, "Error al actualizar datos", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@Ajustes, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
        }
        Globales.correo_electronico=etCorreoAjustes.text.toString()
    }
}