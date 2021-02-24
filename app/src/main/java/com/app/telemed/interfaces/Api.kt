package com.app.telemed.interfaces

import com.app.telemed.models.LoginResponse
import com.app.telemed.models.PasswordRestoreResponse
import retrofit2.http.*

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

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("logout")
    suspend fun logOut(@Header("Authorization") auth: String): PasswordRestoreResponse


}