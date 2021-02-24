package com.app.telemed


import com.app.telemed.models.PasswordRestoreResponse
import com.app.telemed.models.LoginResponse
import com.app.telemed.interfaces.Api
import com.app.telemed.interfaces.Lesson
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.Comment
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class RepositoryImpl(private val api: Api, private val db: AppDatabase): Repository {

    val token by lazy {
        "Bearer ${db.loginDao().get()?.token}"
    }

    override fun getClient(): String {
        return "hello"
    }

    override suspend fun logIn(email: String, pass: String): Flow<LoginResponse> {
        return flow {
            val loginResponse = api.logIn(email, pass)
            db.loginDao().insert(loginResponse)
            emit(loginResponse)
        }.flowOn(IO)
    }

    override suspend fun logOut(): Flow<PasswordRestoreResponse?> {
        return flow {
            val response = api.logOut(token)
            if(response.status == "success") {
                db.clearAllTables()
                emit(response)
            }else {
                emit(null)
            }
        }.flowOn(IO)
    }

    override suspend fun isLogged(): Flow<LoginResponse?> {
        return flow {
            val loginResponse = db.loginDao().get()
            if(loginResponse != null)
                emit(loginResponse)
            else
                emit(null)
        }.flowOn(IO)
    }

    override suspend fun restoreEmail(email: String): Flow<PasswordRestoreResponse> {
        return flow {
            val string = api.restorePassword(email)
            emit(string)
        }.flowOn(IO)
    }

    fun getFakeEvent(): Lesson = Lesson().apply {
            val calendar: Calendar = Calendar.getInstance()
            calendar.setDay(Random.nextInt(1, 25))
            calendar.setHourOfDay(Random.nextInt(1, 24))
            date = calendar
            passed = Random.nextInt(0, 3)
            isSighned = Random.nextBoolean()
            title = "Гр. ${if(isPassed()) "Закончилось" else if(isSoon()) "Скоро" else "Не скоро"}"
        }

    override fun getEvents(): Flow<List<Lesson>> {
        return flow {
            kotlinx.coroutines.delay(3000)
            emit(getFakeEvents())
        }
    }

    override fun getEvent(): Flow<Lesson?> {
        return flow {
            kotlinx.coroutines.delay(2000)
            emit(
                if(Random.nextBoolean())
                    getFakeEvent()
                else null
            )
        }
    }

    override fun getQuestions(): Flow<List<Question>?> {
        return flow {
            val question = mutableListOf<Question>()
            question.add(Question(
                question = "Насколько силен болевой синдром?\n" +
                        "по шкале от 1го до 10ти",
                type = QuestionType.Mark
                ))

            question.addAll(
                    if(BuildConfig.IS_REHUB){
                        List(1) {
                            Question(
                                    type = QuestionType.Select,
                                    variants = listOf(
                                            "Вариант №1",
                                            "Вариант №2",
                                            "Вариант №3",
                                            "Вариант №4"
                                    )
                            )
                        }
                    }else {
                        List(5){
                            Question(
                                    type = QuestionType.Select,
                                    variants = listOf(
                                            "Вариант №1",
                                            "Вариант №2",
                                            "Вариант №3",
                                            "Вариант №4"
                                    )
                            )}
                    }
            )
            question.add(Question(type = QuestionType.Comment))
            emit(question)
        }
    }

    override fun getComments(): Flow<List<Comment>?> {
        return flow {

            kotlinx.coroutines.delay(2000)
            emit(getFakeComments())
        }
    }

    private fun getFakeComments(): List<Comment> {
        return List(30){ getFakeComment(it) }
    }

    private fun getFakeComment(i: Int): Comment {
        return Comment(
            id = i,
            groupText = "Группа№$i Занятие№$i",
            commentText = "Комментарий от доктора написан тут в полном объеме и может быть размером в любое количество символов. Ничего не обрезается троеточиями и т.д",
            dataText = "12 июня 2020  23:45",
                isSeen = Random.nextBoolean()
        )
    }

    private fun getFakeEvents(): List<Lesson> {
        return List(30){ getFakeEvent() }
    }

}

