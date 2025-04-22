package com.corsoft.hitfactor.data.user.api.model

data class User(
    val name: String,
    val lastName: String,
    val login: String,
    val email: String,
    val phoneNumber: String,
    val uuid: String,
)