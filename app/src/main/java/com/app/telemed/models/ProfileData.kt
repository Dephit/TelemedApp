package com.app.telemed.models

import java.io.Serializable

data class ProfileData(
    var created_at: String = "",
    var promo_code: String = "",
    var updated_at: String = "",
    var age: String = "",
    var avatar: String = "",
    var email: String = "",
    var gender: String = "",
    var goal_id: String = "",
    var height: String = "",
    var id: Int = 0,
    var name: String = "",
    var nearest_exercise: String = "",
    var phone: String = "",
    var surname: String = "",
    var user_type: String = "",
    var weight: String = ""
): Serializable