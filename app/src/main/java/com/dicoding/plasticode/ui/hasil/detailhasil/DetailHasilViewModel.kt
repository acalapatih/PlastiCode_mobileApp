package com.dicoding.plasticode.ui.hasil.detailhasil

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.plasticode.data.UserModel
import com.dicoding.plasticode.response.GetPlastikResponse
import com.dicoding.plasticode.service.ApiConfig
import com.dicoding.plasticode.service.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailHasilViewModel: ViewModel() {
    private val _getPlastik = MutableLiveData<GetPlastikResponse.Data>()
    val getPlastik: LiveData<GetPlastikResponse.Data> = _getPlastik

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getPlastik(context: Context, jenisPlastik: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getPlastik(jenisPlastik)
        client.enqueue(object : Callback<GetPlastikResponse> {
            override fun onResponse(
                call: Call<GetPlastikResponse>,
                response: Response<GetPlastikResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _getPlastik.value = response.body()?.data
                } else {
                    Toast.makeText(context, "Fail: ${response.message()}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<GetPlastikResponse>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}