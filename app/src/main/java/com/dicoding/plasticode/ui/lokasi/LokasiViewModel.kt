package com.dicoding.plasticode.ui.lokasi

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.plasticode.network.GoogleMapsApiConfig
import com.dicoding.plasticode.response.GetLokasiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LokasiViewModel : ViewModel() {
    private val _getLokasi = MutableLiveData<List<GetLokasiResponse.ResultsItem>>()
    val getLokasi: LiveData<List<GetLokasiResponse.ResultsItem>> = _getLokasi

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getLokasi(context: Context, location: String, radius: Int, keyword: String, key: String) {
        _isLoading.value = true
        val client = GoogleMapsApiConfig.getApiService().getNearbyLocation(location, radius, keyword, key)
        client.enqueue(object : Callback<GetLokasiResponse> {
            override fun onResponse(
                call: Call<GetLokasiResponse>,
                response: Response<GetLokasiResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _getLokasi.value = response.body()?.results ?: emptyList()
                    Log.d(TAG, response.body()?.status.toString())
                } else {
                    Toast.makeText(
                        context,
                        "onFailure: ${response.errorBody().toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onFailure: ${response.errorBody()}")
                    println("onFailure: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<GetLokasiResponse>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
                println("onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "MapsViewModel"
    }
}