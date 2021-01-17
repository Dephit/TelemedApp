package com.app.telemed.interfaces

import kotlinx.coroutines.flow.Flow

interface Repository{
    fun getClient():String
    suspend fun logIn(email: String, pass: String): Flow<Int>
    suspend fun restoreEmail(email: String): Flow<Int>
}