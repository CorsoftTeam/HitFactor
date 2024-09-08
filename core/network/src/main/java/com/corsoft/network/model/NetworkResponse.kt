package com.corsoft.network.model

import ppk.app.core.network.util.displayedError

sealed interface NetworkResponse<out T> {
    data class Success<out R>(val data: R) : NetworkResponse<R>
    data class Failed(val throwable: Throwable, val code: Int? = null) : NetworkResponse<Nothing> {
        fun getErrorMessage(): String {
            return throwable.displayedError()
        }
    }
}
