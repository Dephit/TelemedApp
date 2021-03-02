package com.app.telemed.interfaces


import com.app.telemed.AppDatabase
import com.app.telemed.fragments.Question
import com.app.telemed.models.*
import com.app.telemed.viewModels.Comment
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Field
import retrofit2.http.Header

interface Repository{
    suspend fun logIn(email: String, pass: String): Flow<LoginResponse?>
    suspend fun logOut(): Flow<PasswordRestoreResponse?>
    suspend fun isLogged(): Flow<LoginResponse?>
    suspend fun restoreEmail(email: String): Flow<PasswordRestoreResponse>

    fun getClient():String

    fun getEvents(): Flow<List<Lesson>>
    fun getEvent(): Flow<Lesson?>
    fun getQuestions(): Flow<List<Question>?>
    fun getComments(): Flow<List<Comment>?>

    fun getProfile(): Flow<Profile>
    fun getGoals(): Flow<GoalResponse>

    fun updateProfile(
        name: String?,
        phone: String?,
        email: String?,
        surname: String?,
        height: Int?,
        weight: Int?,
        age: Int?,
        gender: String?,
        goal_id: Int?,
    ): Flow<UpdateProfileResponse>

    fun getDB(): AppDatabase
}