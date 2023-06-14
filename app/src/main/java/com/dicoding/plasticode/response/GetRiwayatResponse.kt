package com.dicoding.plasticode.response

import com.google.gson.annotations.SerializedName

data class GetRiwayatResponse(

	@field:SerializedName("status_code")
	val statusCode: Int,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("success_message")
	val successMessage: String,

	@field:SerializedName("histories")
	val histories: List<HistoriesItem>,

	@field:SerializedName("error")
	val error: Boolean
) {
	data class HistoriesItem(

		@field:SerializedName("updated_at")
		val updatedAt: String,

		@field:SerializedName("user_id")
		val userId: Int,

		@field:SerializedName("detail_masa_pakai")
		val detailMasaPakai: Any,

		@field:SerializedName("created_at")
		val createdAt: String,

		@field:SerializedName("detail_tingkat_bahaya")
		val detailTingkatBahaya: Any,

		@field:SerializedName("id")
		val id: Int,

		@field:SerializedName("jenis_plastik")
		val jenisPlastik: Any,

		@field:SerializedName("detail_jenis_plastik")
		val detailJenisPlastik: Any,

		@field:SerializedName("masa_pakai")
		val masaPakai: Any,

		@field:SerializedName("tingkat_bahaya")
		val tingkatBahaya: Any,

		@field:SerializedName("url_image")
		val urlImage: String
	)
}
