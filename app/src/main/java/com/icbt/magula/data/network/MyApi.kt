package com.icbt.magula.data.network

import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @POST("login")
    fun login(@Body login: Login) : Call<ResponseBody>

    @POST("api/v1/registration")
    fun registration(@Body registration: Registration) : Call<ResponseBody>

    @POST("api/v1/registration/service")
    fun serviceRegistration(@Body serviceRegistration:ServiceRegistration):Call<ResponseBody>

    @GET("api/v1/users")
    suspend fun getUsers():Response<List<ServiceResponse>>

    @POST("api/v1/users")
    suspend fun getSuitableUsers(@Body selectServiceRequest: SelectServiceRequest)
            :Response<List<ServiceResponse>>

    companion object{
        operator fun invoke():MyApi{
            return Retrofit.Builder()
                    .baseUrl("https://test-wedding-app.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MyApi::class.java)
        }
    }

}