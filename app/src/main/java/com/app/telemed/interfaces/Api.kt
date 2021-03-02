package com.app.telemed.interfaces

import com.app.telemed.models.*
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

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("profiles")
    suspend fun profile(@Header("Authorization") auth: String): Profile

    @FormUrlEncoded
    @Headers("Content-Type: application/json;charset=UTF-8")
    @PUT("profiles")
    suspend fun updateProfile(
        @Header("Authorization") auth: String,
        @Field("name") name: String?,
        @Field("phone") phone: String?,
        @Field("email") email: String?,
        @Field("surname") surname: String?,
        @Field("height") height: Int?,
        @Field("weight") weight: Int?,
        @Field("age") age: Int?,
        @Field("gender") gender: String?,
        @Field("goal_id") goal_id: Int?,
        //@Field("avatar") avatar: String?,
    ): UpdateProfileResponse

    @GET("goals")
    suspend fun getGoals(): GoalResponse



}
