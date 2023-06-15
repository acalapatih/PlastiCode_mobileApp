package com.dicoding.plasticode.response

import com.google.gson.annotations.SerializedName

data class PostImageResponse(

	@field:SerializedName("error_message")
	val errorMessage: String,

	@field:SerializedName("status_code")
	val statusCode: Int,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("success_message")
	val successMessage: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("history_id")
	val historyId: Int
)
