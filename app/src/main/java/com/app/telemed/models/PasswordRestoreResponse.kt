package com.app.telemed.models

data class PasswordRestoreResponse(
    var status: String = "",
    var message: String = "",
    var data: String = ""
)

data class ProfileErrorResponse(
    var errors: List<Error> = listOf(),
    var message: String = ""
)


