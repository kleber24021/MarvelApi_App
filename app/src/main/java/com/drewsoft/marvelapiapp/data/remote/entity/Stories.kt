package com.drewsoft.marvelapiapp.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Stories(

	@SerializedName("collectionURI")
	val collectionURI: String? = null,

	@SerializedName("available")
	val available: Int? = null,

	@SerializedName("returned")
	val returned: Int? = null,

	@SerializedName("items")
	val items: List<ItemsItem?>? = null
)