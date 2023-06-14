package com.dicoding.plasticode.ui.deteksi

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.plasticode.response.PostImageResponse
import com.dicoding.plasticode.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DeteksiViewModel: ViewModel() {
    private val _postImage = MutableLiveData<PostImageResponse>()
    val postImage: LiveData<PostImageResponse> = _postImage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postImage(context: Context, image: File) {
//        val imageFIle = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
//        val imageFileMultipart = MultipartBody.Part.createFormData(
//            "photo",
//            image.name,
//            imageFIle
//        )
        _isLoading.value = true
        val client = ApiConfig.getApiService().postImage(image)
        client.enqueue(object : Callback<PostImageResponse> {
            override fun onResponse(
                call: Call<PostImageResponse>,
                response: Response<PostImageResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _postImage.value = response.body()
                } else {
                    Toast.makeText(context, "Fail: ${response.message()}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<PostImageResponse>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}