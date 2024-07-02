package com.drewsoft.marvelapiapp.domain.model

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class Link(
    val url: String,
    val linkType: LinkType
) {
    val urlEncoded: String
        get() = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
    enum class LinkType {
        DETAIL, WIKI, COMIC, OTHER
    }

    constructor(
        url: String,
        type: String
    ) : this(
        url, when (type) {
            "detail" -> LinkType.DETAIL
            "wiki" -> LinkType.WIKI
            "comicLink", "comiclink" -> LinkType.COMIC
            else -> LinkType.OTHER
        }
    )
}