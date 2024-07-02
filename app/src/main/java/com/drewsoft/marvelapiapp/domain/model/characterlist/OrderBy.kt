package com.drewsoft.marvelapiapp.domain.model.characterlist

import androidx.annotation.StringRes
import com.drewsoft.marvelapiapp.R

enum class OrderBy(
    @StringRes val textToShow: Int
) {
    NAME(R.string.sort_by_name),
    MODIFIED(R.string.sort_by_date),
    DESC_NAME(R.string.sort_by_desc_name),
    DESC_MODIFIED(R.string.sort_by_desc_date)
}