package com.app.telemed

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import com.app.telemed.interfaces.BaseViewModelInterface
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.BaseViewModel

open class QuestionBaseViewModel(repository: Repository, savedStateHandle: SavedStateHandle):
    BaseViewModel(repository, savedStateHandle), IQuestionBaseViewModel {

    override val CURRENT_QUESTION: String = "CURRENT_QUESTION"
    override val QUESTIONS: String = "QUESTIONS"

    var quest: List<Question> = listOf()
    var position: Int = 0

    override fun getQuests(): List<Question> {
        return quest
    }

    override fun getCurrentPosition(): Int {
        return position
    }

    override fun getCurrentQuestion() = quest[position]

    override fun restoreState(it: Bundle) {
        position = it.getInt(CURRENT_QUESTION, 0)
        quest = it.getParcelableArrayList(QUESTIONS)!!
    }
}

interface IQuestionBaseViewModel : BaseViewModelInterface {

    val CURRENT_QUESTION: String
    val QUESTIONS: String

    fun restoreState(it: Bundle)
    fun getQuests(): List<Question>
    fun getCurrentPosition(): Int
    fun getCurrentQuestion(): Question
}
