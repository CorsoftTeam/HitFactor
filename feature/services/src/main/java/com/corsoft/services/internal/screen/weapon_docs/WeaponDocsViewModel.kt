package com.corsoft.services.internal.screen.weapon_docs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.corsoft.common.FirebaseEventsEnum
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.analytics.api.AnalyticsRepository
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.hitfactor.data.user.api.entities.GunDocumentsEntity
import com.ramcosta.composedestinations.generated.services.destinations.WeaponDocsScreenDestination
import kotlinx.coroutines.launch

internal class WeaponDocsViewModel(
    savedStateHandle: SavedStateHandle,
    analyticsRepository: AnalyticsRepository,
    private val userRepository: UserRepository
) : MviViewModel<WeaponDocsScreenState, WeaponDocsAction, WeaponDocsEffect>(
    WeaponDocsScreenState()
) {

    private val navArgs = WeaponDocsScreenDestination.argsFrom(savedStateHandle)

    override fun onAction(action: WeaponDocsAction) {
        when(action) {
            is WeaponDocsAction.ChangeDocFirstPageUri -> changeDocsUri(docFirstUri = action.uri)
            is WeaponDocsAction.ChangeDocSecondPageUri -> changeDocsUri(docSecondUri = action.uri)
            is WeaponDocsAction.ChangeLicenseFirstPageUri -> changeDocsUri(licenseFirstUri = action.uri)
            is WeaponDocsAction.ChangeLicenseSecondPageUri -> changeDocsUri(licenseSecondUri = action.uri)
        }
    }

    private fun changeDocsUri(
        docFirstUri: String? = null,
        docSecondUri: String? = null,
        licenseFirstUri: String? = null,
        licenseSecondUri: String? = null,
    ) {
        viewModelScope.launch {
            userRepository.setGunDocPhotoUrl(
                navArgs.gunId,
                docFirstUri ?: uiState.value.gunDocs.docFirstPageUri,
                docSecondUri ?: uiState.value.gunDocs.docSecondPageUri,
                licenseFirstUri ?: uiState.value.gunDocs.licenseFirstPageUri,
                licenseSecondUri ?: uiState.value.gunDocs.licenseSecondPageUri
            )
            update()
        }
    }

    init {
        analyticsRepository.sendEvent(FirebaseEventsEnum.OPEN_DOCS.key)
        update()
    }
    private fun update() {
        viewModelScope.launch {
            val gunDocs = userRepository.getGunDocs(navArgs.gunId) ?: GunDocumentsEntity(gunId = navArgs.gunId)
            changeState {
                it.copy(
                    gunDocs = gunDocs
                )
            }
        }
    }

}