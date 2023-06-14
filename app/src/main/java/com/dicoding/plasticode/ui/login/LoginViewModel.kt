package com.dicoding.plasticode.ui.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.plasticode.data.UserModel
import com.dicoding.plasticode.response.Login
import com.dicoding.plasticode.service.UserPreference
import com.dicoding.plasticode.service.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private val preference: UserPreference
): ViewModel() {
    private val _postLogin = MutableLiveData<Login>()
    val postLogin: LiveData<Login> = _postLogin

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            preference.saveUser(user)
        }
    }

    fun postLogin(context: Context, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().loginWithToken(email, password)
        client.enqueue(object : Callback<Login> {
            override fun onResponse(
                call: Call<Login>,
                response: Response<Login>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val token = response.body()?.data?.token as String
                    val idUser = response.body()?.data?.id
                    val name = response.body()?.data?.name
                    val email = response.body()?.data?.email
                    _postLogin.value = response.body()
                    if (idUser != null && name != null && email != null) {
                        saveUser(UserModel(token, idUser, name, email, true))
                        Toast.makeText(context, "Selamat Datang di PlastiCode", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Fail: ${response.message()}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}