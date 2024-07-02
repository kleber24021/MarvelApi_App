package com.drewsoft.marvelapiapp.domain.model

data class Character(
    val id: Int, val name: String, val description: String, private val thumbnail: Thumbnail
) {
    fun getImageUrl(
        imageFormat: ImageFormat
    ): String {
        val baseUrl = if (thumbnail.path.startsWith("http")){
            thumbnail.path.replace("http", "https")
        }else{
            thumbnail.path
        }
        val imageFormatString = when (imageFormat) {
            is ImageFormat.LANDSCAPE -> "landscape"
            is ImageFormat.SQUARE -> "standard"
        }
        val sizeString = when (imageFormat.imageSize) {
            ImageFormat.ImageSize.SMALL -> "_small"
            ImageFormat.ImageSize.MEDIUM -> "_medium"
            ImageFormat.ImageSize.LARGE -> "_large"
            ImageFormat.ImageSize.XLARGE -> "_xlarge"
            ImageFormat.ImageSize.AMAZING -> "_amazing"
        }
        val extension: String = thumbnail.extension
        return "$baseUrl/$imageFormatString$sizeString.$extension"
    }
}

data class Thumbnail(
    val path: String, val extension: String
)
