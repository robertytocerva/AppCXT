package com.cxt.robertytocerva.cxt.api
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
    @POST("agregarTutor")
    suspend fun agregarTutor(@Body nuevoTutor: TutorEnvio): retrofit2.Response<Void>
    @POST("agregarNino")
    suspend fun agregarNino(@Body nuevoNino: NinoRegistro): retrofit2.Response<Void>

}