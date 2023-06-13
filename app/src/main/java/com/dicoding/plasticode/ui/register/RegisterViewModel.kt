package com.dicoding.plasticode.ui.register

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.plasticode.response.RegisterResponse
import com.vicryfahreza.storyapp.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {
    private val _postRegister = MutableLiveData<RegisterResponse>()
    val postRegister: LiveData<RegisterResponse> = _postRegister

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postRegister(context: Context, name: String, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().registerAccount(name, email, password)
        client.enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if(response.isSuccessful) {
                    _postRegister.value = response.body()
                    _isLoading.value = false
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}