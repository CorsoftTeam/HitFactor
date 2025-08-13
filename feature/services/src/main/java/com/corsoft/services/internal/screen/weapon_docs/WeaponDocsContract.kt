package com.corsoft.services.internal.screen.weapon_docs

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.hitfactor.data.user.api.entities.GunDocumentsEntity

@Immutable
data class WeaponDocsScreenState(
    val isLoading: Boolean = false,
    val showFullscreenUri: String? = null,
    val gunDocs: GunDocumentsEntity = GunDocumentsEntity("")
) : MviState

internal sealed interface WeaponDocsAction : MviAction {
    data class ChangeDocFirstPageUri(val uri: String) : WeaponDocsAction
    data class ChangeDocSecondPageUri(val uri: String) : WeaponDocsAction
    data class ChangeLicenseFirstPageUri(val uri: String) : WeaponDocsAction
    data class ChangeLicenseSecondPageUri(val uri: String) : WeaponDocsAction
}

internal sealed interface WeaponDocsEffect : MviEffect