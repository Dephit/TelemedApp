package com.app.telemed

import com.app.telemed.interfaces.Api
import com.app.telemed.interfaces.Lesson
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.Comment
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
            if(BuildConfig.IS_REHUB){
                question.add(
                    Question(
                        type = QuestionType.Select,
                        variants = listOf(
                            "Вариант №1",
                            "Вариант №2",
                            "Вариант №3",
                            "Вариант №4"
                        )
                    ))
            }else {
                for (i in 0 .. 5){
                    question.add(
                        Question(
                            type = QuestionType.Select,
                            variants = listOf(
                                "Вариант №1",
                                "Вариант №2",
                                "Вариант №3",
                                "Вариант №4"
                            )
                        ))
                }
            }
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
        val list = mutableListOf<Comment>()
        for (i in 0 .. 15){
            list.add(getFakeComment(i))
        }
        return list
    }

    private fun getFakeComment(i: Int): Comment {
        return Comment(
            id = i,
            groupText = "Группа№15 Занятие№15",
            commentText = "Комментарий от доктора написан тут в полном объеме и может быть размером в любое количество символов. Ничего не обрезается троеточиями и т.д",
            dataText = "12 июня 2020  23:45"
        )
    }

    private fun getFakeEvents(): List<Lesson> {
        val list = mutableListOf<Lesson>()
        for (i in 0 .. 15){
            list.add(getFakeEvent())
        }
        return list
    }

}

