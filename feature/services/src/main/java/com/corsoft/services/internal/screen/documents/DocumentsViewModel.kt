package com.corsoft.services.internal.screen.documents

import com.corsoft.common.FirebaseEventsEnum
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.analytics.api.AnalyticsRepository

internal class DocumentsViewModel(
    analytics: AnalyticsRepository
) :
    MviViewModel<DocumentsScreenState, DocumentsAction, DocumentsEffect>(
        DocumentsScreenState()
    ) {
    override fun onAction(action: DocumentsAction) {

    }

    init {
        analytics.sendEvent(FirebaseEventsEnum.OPEN_DOCS.key)
    }

}