package com.drewsoft.marvelapiapp.domain.model


sealed class ImageFormat(val imageSize: ImageSize){
    class SQUARE(imageSize: ImageSize):ImageFormat(imageSize)
    class LANDSCAPE(imageSize: ImageSize):ImageFormat(imageSize)

    enum class ImageSize{
        SMALL, MEDIUM, LARGE, XLARGE, AMAZING
    }
}