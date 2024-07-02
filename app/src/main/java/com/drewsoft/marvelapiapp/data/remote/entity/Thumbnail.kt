package com.drewsoft.marvelapiapp.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Thumbnail(

	@SerializedName("path")
	val path: String? = null,

	@SerializedName("extension")
	val extension: String? = null
)