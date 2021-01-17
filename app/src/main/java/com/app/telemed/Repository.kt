package com.app.telemed

import com.app.telemed.interfaces.Api
import com.app.telemed.interfaces.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class RepositoryImpl(api: Api): Repository {
    override fun getClient(): String {
        return "hello"
    }

    override suspend fun logIn(email: String, pass: String): Flow<Int> {
        return flow {
            kotlinx.coroutines.delay(2000)
            emit(if(email.contains("0")) 1 else -1)
        }
    }

    override suspend fun restoreEmail(email: String): Flow<Int> {
        return flow {
            kotlinx.coroutines.delay(2000)
            emit(if(email.contains("0")) 1 else -1)
        }
    }

}

