package com.dicoding.plasticode.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("name")
	val name: List<String>,

	@field:SerializedName("password")
	val password: List<String>,

	@field:SerializedName("email")
	val email: List<String>
)
