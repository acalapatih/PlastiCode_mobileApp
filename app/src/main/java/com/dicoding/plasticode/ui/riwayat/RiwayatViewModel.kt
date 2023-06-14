package com.dicoding.plasticode.ui.riwayat

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.plasticode.data.UserModel
import com.dicoding.plasticode.response.GetRiwayatResponse
import com.dicoding.plasticode.service.ApiConfig
import com.dicoding.plasticode.service.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatViewModel(
    private val preference: UserPreference
) : ViewModel() {
    private val _getRiwayat = MutableLiveData<List<GetRiwayatResponse.HistoriesItem>>()
    val getRiwayat: LiveData<List<GetRiwayatResponse.HistoriesItem>> = _getRiwayat

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

    fun getRiwayat(context: Context, idUser: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRiwayat(idUser)
        client.enqueue(object : Callback<GetRiwayatResponse> {
            override fun onResponse(
                call: Call<GetRiwayatResponse>,
                response: Response<GetRiwayatResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _getRiwayat.value = response.body()?.histories
                } else {
                    Toast.makeText(context, "Fail: ${response.message()}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<GetRiwayatResponse>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}