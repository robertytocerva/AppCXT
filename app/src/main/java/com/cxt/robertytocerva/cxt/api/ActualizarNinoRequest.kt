package com.cxt.robertytocerva.cxt.api

data class ActualizarNinoRequest(
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val correo_electronico: String
)