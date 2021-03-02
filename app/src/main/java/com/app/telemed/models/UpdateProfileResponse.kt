package com.app.telemed.models

data class UpdateProfileResponse(
    var data: List<Data> = listOf(),
    var errors: List<ProfileData> = listOf(),
    var message: String = "",
    var status: String = ""
)