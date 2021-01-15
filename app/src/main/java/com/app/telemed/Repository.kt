package com.app.telemed

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl(api: Api): Repository{
    override fun getClient(): String {
        return "hello"
    }

}

interface Repository{
    fun getClient():String
}