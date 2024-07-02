package com.drewsoft.marvelapiapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Data(

	@SerializedName("total")
	val total: Int? = null,

	@SerializedName("offset")
	val offset: Int? = null,

	@SerializedName("limit")
	val limit: Int? = null,

	@SerializedName("count")
	val count: Int? = null,

	@SerializedName("results")
	val results: List<ResultsItem?>? = null
)