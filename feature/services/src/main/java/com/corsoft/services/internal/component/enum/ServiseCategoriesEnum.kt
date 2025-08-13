package com.corsoft.services.internal.component.enum

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.corsoft.resources.CoreStringRes

enum class ServiceCategoriesEnum (val key: String){
    ALL("all"),
    SPORTSMAN("sportsman"),
    HUNTER("hunter"),
    CUSTOM("custom")
    ;

    @Composable
    fun getName(): String =
        when (this) {
            ALL -> stringResource(id = CoreStringRes.all)
            SPORTSMAN -> stringResource(id = CoreStringRes.sportsman)
            HUNTER -> stringResource(id = CoreStringRes.hunter)
            CUSTOM -> stringResource(id = CoreStringRes.custom)
        }
}