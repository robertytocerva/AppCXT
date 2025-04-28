package com.cxt.robertytocerva.cxt.api

data class ActualizarTutorRequest(
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val correo_electronico: String
)