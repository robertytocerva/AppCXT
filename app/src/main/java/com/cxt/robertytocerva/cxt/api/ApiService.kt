package com.cxt.robertytocerva.cxt.api
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
    @POST("agregarTutor")
    suspend fun agregarTutor(@Body nuevoTutor: TutorEnvio): retrofit2.Response<Void>
    @POST("agregarNino")
    suspend fun agregarNino(@Body nuevoNino: NinoRegistro): retrofit2.Response<Void>
    @GET("frase")
    suspend fun obtenerFrase(): Frase
    @POST("ultima-sesion")
    suspend fun obtenerUltimaSesion(@Body sesionRequest: SesionRequest): Sesion
    @POST("nombre-nino")
    suspend fun obtenerNombreNino(@Body nameNinoRequest: NameNinoRequest): NameNino
    @POST("progreso-home")
    suspend fun obtenerProgresoHome(@Body progresoHomeRequest: ProgresoHomeRequest): ProgresoHome
    @GET("juegos")
    suspend fun obtenerJuegos(): List<Juego>
    @POST("buscarTutor")
    suspend fun buscarTutor(@Body correo: TutorRequest): Tutor
    @POST("buscarNino")
    suspend fun buscarNino(@Body correo: NinoRequest): Nino
    @PUT("actualizarNino")
    suspend fun actualizarNino(@Body actualizarNinoRequest: ActualizarNinoRequest): retrofit2.Response<Void>
    @PUT("actualizarTutor")
    suspend fun actualizarTutor(@Body actualizarTutorRequest: ActualizarTutorRequest): retrofit2.Response<Void>
    @POST("mejor-sesion")
    suspend fun obtenerMejorSesion(@Body correoRequest: TutorRequest): MejorSesionResponse
    @POST("progreso-nino")
    suspend fun obtenerProgresoPorNino(@Body request: IdNinoRequest): ProgresoNinoResponse
    @POST("discapacidad")
    suspend fun obtenerDiscapacidad(@Body request: TutorRequest): DiscapacidadResponse
    @POST("recomendaciones")
    suspend fun obtenerRecomendaciones(@Body request: DiscapacidadRequest): List<RecomendacionResponse>







}