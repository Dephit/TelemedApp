package com.app.telemed.interfaces

import com.app.telemed.Question
import kotlinx.coroutines.flow.Flow

interface Repository{
    fun getClient():String
    suspend fun logIn(email: String, pass: String): Flow<Int>
    suspend fun restoreEmail(email: String): Flow<Int>
    fun getEvents(): Flow<List<Lesson>>
    fun getEvent(): Flow<Lesson?>
    abstract fun getQuestions(): Flow<List<Question>?>
}