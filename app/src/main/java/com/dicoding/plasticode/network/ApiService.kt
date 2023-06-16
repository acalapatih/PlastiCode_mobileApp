package com.dicoding.plasticode.network

import com.dicoding.plasticode.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun loginWithToken(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<PostLoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun registerAccount(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ) : Call<PostRegisterResponse>

    @Multipart
    @POST("image")
    fun postImage(
        @Part file: MultipartBody.Part,
    ) : Call<PostImageResponse>

    @GET("plastic/{jenisPlastik}")
    fun getPlastik(
        @Path("jenisPlastik") jenisPlastik: String,
    ) : Call<GetPlastikResponse>

    @FormUrlEncoded
    @PUT("history-update/{idRiwayat}")
    fun putRiwayat(
        @Path("idRiwayat") idRiwayat: Int,
        @Field("jenis_plastik") jenisPlastik: String,
        @Field("masa_pakai") masaPakai: String,
        @Field("tingkat_bahaya") tingkatBahaya: String,
        @Field("detail_jenis_plastik") detailJenis: String,
        @Field("detail_masa_pakai") detailMasa: String,
        @Field("detail_tingkat_bahaya") detailBahaya: String,
    ) : Call<PutRiwayatResponse>

    @GET("user-histories/{idUser}")
    fun getRiwayat(
        @Path("idUser") idUser: Int,
    ) : Call<GetRiwayatResponse>

}