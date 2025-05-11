package com.cxt.robertytocerva.cxt.api

data class MejorSesionResponse(
    val id_nino: Int,
    val nivel_actual: String,
    val puntos_por_juego: Int,
    val nombre_juego: String
)
