package com.dicoding.plasticode.ui.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.plasticode.data.UserModel
import com.dicoding.plasticode.response.LoginResponse
import com.dicoding.plasticode.service.UserPreference
import com.vicryfahreza.storyapp.service.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference): ViewModel() {
    private val _postLogin = MutableLiveData<Boolean>()
    val postLogin: LiveData<Boolean> = _postLogin

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun postLogin(context: Context, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().loginWithToken(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val token = response.body()?.token as String
                    _postLogin.value = true
                    saveUser(UserModel(token, true))
                    Toast.makeText(context, "Welcome to Story App", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Fail: ${response.message()}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}