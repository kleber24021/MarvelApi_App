package com.drewsoft.marvelapiapp.domain.model.characterlist

data class FilterCriteria(
    val name: String? = null,
    val order: OrderBy? = null,
    val offset: Int = 0
)