package com.cxt.robertytocerva.cxt.api

data class Nino(
    val id_nino: Int,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val id_discapacidad: Int,
    val correo_electronico: String
)