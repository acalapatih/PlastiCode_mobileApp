package com.dicoding.plasticode.response

import com.google.gson.annotations.SerializedName

data class GetPlastikResponse(

	@field:SerializedName("status_code")
	val statusCode: Int,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("success_message")
	val successMessage: String,

	@field:SerializedName("error")
	val error: Boolean
) {
	data class Data(

		@field:SerializedName("updated_at")
		val updatedAt: String,

		@field:SerializedName("detail_masa_pakai")
		val detailMasaPakai: String,

		@field:SerializedName("created_at")
		val createdAt: String,

		@field:SerializedName("detail_tingkat_bahaya")
		val detailTingkatBahaya: String,

		@field:SerializedName("id")
		val id: Int,

		@field:SerializedName("jenis_plastik")
		val jenisPlastik: String,

		@field:SerializedName("detail_jenis_plastik")
		val detailJenisPlastik: String,

		@field:SerializedName("masa_pakai")
		val masaPakai: String,

		@field:SerializedName("tingkat_bahaya")
		val tingkatBahaya: String
	)
}