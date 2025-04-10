package com.corsoft.services.internal.screen.documents

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.model.ServiceModel

@Immutable
data class DocumentsScreenState(
    val isLoading: Boolean = true
) : MviState

internal sealed interface DocumentsAction : MviAction

internal sealed interface DocumentsEffect : MviEffect