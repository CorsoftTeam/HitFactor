package com.corsoft.services.internal.component.enum

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.corsoft.resources.CoreRawRes
import com.corsoft.resources.CoreStringRes

internal enum class DocumentsEnum {
    FZ_150,
    IPSC_RULES,
    ;

    @Composable
    fun getName(): String =
        when (this) {
            FZ_150 -> stringResource(CoreStringRes.federal_law_150)
            IPSC_RULES -> stringResource(CoreStringRes.rules)
        }

    @Composable
    fun getDescription(): String =
        when (this) {
            FZ_150 -> stringResource(CoreStringRes.law_desc)
            IPSC_RULES -> stringResource(CoreStringRes.rules_desc)
        }

    @Composable
    fun getResId(): Int =
        when (this) {
            FZ_150 -> CoreRawRes.doc_fz_150
            IPSC_RULES -> CoreRawRes.doc_ipsc_rules
        }
}