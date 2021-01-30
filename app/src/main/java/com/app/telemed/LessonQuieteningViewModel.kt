package com.app.telemed

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.BaseViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LessonQuieteningViewModel @ViewModelInject constructor(
    val repository: Repository,
    @Assisted
private val savedStateHandle: SavedStateHandle
) : QuestionBaseViewModel(repository, savedStateHandle){

    init {
        getQuestions()
    }


    override fun getQuests(): List<Question> {
        return quest
    }


    private fun getQuestions() {
        viewModelScope.launch {
            repository.getQuestions()
                .collect {_questions->
                    if (_questions != null) {
                        quest = _questions
                    }
                    modelState.value = ModelState.Success(quest)
                }
        }
    }
}