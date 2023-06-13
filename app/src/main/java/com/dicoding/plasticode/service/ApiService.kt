package com.dicoding.plasticode.service

import com.dicoding.plasticode.response.Login
import com.dicoding.plasticode.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun loginWithToken(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<Login>

    @FormUrlEncoded
    @POST("register")
    fun registerAccount(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ) : Call<RegisterResponse>



}