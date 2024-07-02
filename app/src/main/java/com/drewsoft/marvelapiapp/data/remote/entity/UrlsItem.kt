package com.drewsoft.marvelapiapp.data.remote.entity

import com.google.gson.annotations.SerializedName

data class UrlsItem(

	@SerializedName("type")
	val type: String? = null,

	@SerializedName("url")
	val url: String? = null
)