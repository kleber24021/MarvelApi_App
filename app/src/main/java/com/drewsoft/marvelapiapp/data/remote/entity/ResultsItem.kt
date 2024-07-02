package com.drewsoft.marvelapiapp.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ResultsItem(

	@SerializedName("thumbnail")
	val thumbnail: Thumbnail? = null,

	@SerializedName("urls")
	val urls: List<UrlsItem?>? = null,

	@SerializedName("stories")
	val stories: Stories? = null,

	@SerializedName("series")
	val series: Series? = null,

	@SerializedName("comics")
	val comics: Comics? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("description")
	val description: String? = null,

	@SerializedName("modified")
	val modified: String? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("resourceURI")
	val resourceURI: String? = null,

	@SerializedName("events")
	val events: Events? = null
)