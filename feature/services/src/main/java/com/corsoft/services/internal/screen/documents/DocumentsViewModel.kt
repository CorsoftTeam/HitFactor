package com.corsoft.services.internal.screen.documents

import com.corsoft.common.mvvm.MviViewModel

internal class DocumentsViewModel :
    MviViewModel<DocumentsScreenState, DocumentsAction, DocumentsEffect>(
        DocumentsScreenState()
    ) {
    override fun onAction(action: DocumentsAction) {

    }

}