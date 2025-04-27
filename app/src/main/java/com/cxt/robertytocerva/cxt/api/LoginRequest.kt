package com.cxt.robertytocerva.cxt.api

data class LoginRequest(
    val correo_electronico: String,
    val contrasena: String
)