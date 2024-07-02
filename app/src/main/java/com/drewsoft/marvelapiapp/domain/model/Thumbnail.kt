package com.drewsoft.marvelapiapp.domain.model

data class Thumbnail(
    val path: String, val extension: String
){
    fun getImageUrl(
        imageFormat: ImageFormat
    ): String {
        val baseUrl = if (this.path.startsWith("http")){
            this.path.replace("http", "https")
        }else{
            this.path
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
        val extension: String = this.extension
        return "$baseUrl/$imageFormatString$sizeString.$extension"
    }
}