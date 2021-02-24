package com.app.telemed.interfaces

import com.app.telemed.models.PasswordRestoreResponse
import com.app.telemed.models.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface Api {

    @FormUrlEncoded
    @POST("login")
    suspend fun logIn(
        @Field("email") first: String?,
        @Field("password") last: String?
    ): LoginResponse

    @FormUrlEncoded
    @POST("forgot_password")
    suspend fun restorePassword(@Field("email")email: String): PasswordRestoreResponse


}