package com.drewsoft.marvelapiapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterDetailResponse(

	@SerializedName("copyright")
	val copyright: String? = null,

	@SerializedName("code")
	val code: Int? = null,

	@SerializedName("data")
	val data: Data? = null,

	@SerializedName("attributionHTML")
	val attributionHTML: String? = null,

	@SerializedName("attributionText")
	val attributionText: String? = null,

	@SerializedName("etag")
	val etag: String? = null,

	@SerializedName("status")
	val status: String? = null
)