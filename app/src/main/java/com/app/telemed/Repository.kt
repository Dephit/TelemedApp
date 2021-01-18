package com.app.telemed

import com.app.telemed.interfaces.Api
import com.app.telemed.interfaces.Lesson
import com.app.telemed.interfaces.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Singleton
import kotlin.random.Random

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

    override fun getEvents(): Flow<List<Lesson>> {
        return flow {
            kotlinx.coroutines.delay(2000)
            val list = mutableListOf<Lesson>()
            for (i in 0 .. 5){
                val lesson = Lesson()
                val calendar: Calendar = Calendar.getInstance()
                calendar.setDay(Random.nextInt(1, 20))
                lesson.date = calendar
                lesson.passed = Random.nextBoolean()
                lesson.title = "Гр. 105"
                list.add(lesson)
            }

            emit(list)
        }
    }

}

