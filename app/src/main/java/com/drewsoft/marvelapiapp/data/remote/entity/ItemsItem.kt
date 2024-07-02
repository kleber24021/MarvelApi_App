package com.drewsoft.marvelapiapp.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ItemsItem(

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("resourceURI")
	val resourceURI: String? = null,

	@SerializedName("type")
	val type: String? = null
)