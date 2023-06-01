package com.dicoding.plasticode.network

import com.dicoding.plasticode.response.GetLokasiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleMapsApiService {
    @GET("maps/api/place/nearbysearch/json")
    fun getNearbyLocation(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("keyword") keyword: String,
        @Query("key") key: String
    ): Call<GetLokasiResponse>
}