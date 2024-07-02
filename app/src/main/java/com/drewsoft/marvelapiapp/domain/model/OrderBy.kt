package com.drewsoft.marvelapiapp.domain.model

import com.drewsoft.marvelapiapp.data.DataConsts

enum class OrderBy(
    val queryString: String
) {
    NAME(DataConsts.NAME_QUERY), MODIFIED(DataConsts.MODIFIED_QUERY), DESC_NAME(DataConsts.DESC_NAME_QUERY), DESC_MODIFIED(
        DataConsts.DESC_MODIDIFIED_QUERY
    )
}