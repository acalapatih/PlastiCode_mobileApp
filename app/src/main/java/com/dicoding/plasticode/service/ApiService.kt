package com.dicoding.plasticode.service

import com.dicoding.plasticode.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

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

    @FormUrlEncoded
    @POST("image")
    fun postImage(
        @Field("image") image: File,
    ) : Call<PostImageResponse>

    @GET("plastic/{jenisPlastik}")
    fun getPlastik(
        @Path("jenisPlastik") jenisPlastik: String,
    ) : Call<GetPlastikResponse>

    @GET("user-histories/{idUser}")
    fun getRiwayat(
        @Path("idUser") idUser: Int,
    ) : Call<GetRiwayatResponse>

}