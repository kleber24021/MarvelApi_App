package com.drewsoft.marvelapiapp.ui.characterlist.model

import com.drewsoft.marvelapiapp.domain.model.OrderBy

data class FilterCriteria(
    val name: String? = null,
    val order: OrderBy? = null,
    val offset: Int = 0
)