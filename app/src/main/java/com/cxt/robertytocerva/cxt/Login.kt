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
import com.cxt.robertytocerva.cxt.api.LoginRequest
import com.cxt.robertytocerva.cxt.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class Login : AppCompatActivity() {

    private lateinit var  btnRegistrar: Button
    private lateinit var  btnIgresar: Button
    private lateinit var  etCorreoLogin: EditText
    private lateinit var  etContrasenaLogin: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        btnRegistrar = findViewById(R.id.btnCrearCuenta)
        btnIgresar = findViewById(R.id.btnIngresar)
        etCorreoLogin = findViewById(R.id.etCorreo)
        etContrasenaLogin = findViewById(R.id.etContrasena)

        etCorreoLogin.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                etCorreoLogin.text.clear()

            }
        }
        etContrasenaLogin.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                etContrasenaLogin.text.clear()
            }
        }
        btnIgresar.setOnClickListener {
            login()
        }
        btnRegistrar.setOnClickListener {
            startActivity(Intent(this, Registro::class.java))
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

    //funcion login
    private fun login() {
        val correo = etCorreoLogin.text.toString()
        val contrasena = etContrasenaLogin.text.toString()

        if (correo.isNotEmpty() && contrasena.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitClient.apiService.login(LoginRequest(correo, contrasena))
                    runOnUiThread {
                        if (response.success) {
                            Toast.makeText(this@Login, "Login exitoso", Toast.LENGTH_SHORT).show()
                            // Aquí puedes abrir tu siguiente Activity
                            Globales.correo_electronico = correo
                            val intent = Intent(this@Login, Home::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@Login, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: HttpException) {
                    runOnUiThread {
                        Toast.makeText(this@Login, "Error de servidor", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@Login, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}