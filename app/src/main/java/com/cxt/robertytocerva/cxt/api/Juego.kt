package com.cxt.robertytocerva.cxt.api

data class Juego(
    val id_juego: Int,
    val nombre_juego: String,
    val descripcion: String,
    val dificultad: String,
    val puntos: Int,
    val cant_niveles: Int,
    val descripcion_completa: String
)
