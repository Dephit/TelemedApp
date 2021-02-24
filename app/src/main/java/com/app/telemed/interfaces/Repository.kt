package com.app.telemed.interfaces


import com.app.telemed.models.PasswordRestoreResponse
import com.app.telemed.models.LoginResponse
import com.app.telemed.Question
import com.app.telemed.viewModels.Comment
import kotlinx.coroutines.flow.Flow

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
}