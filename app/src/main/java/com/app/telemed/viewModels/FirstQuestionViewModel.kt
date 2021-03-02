package com.app.telemed.viewModels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.app.telemed.interfaces.Repository

class FirstQuestionViewModel @ViewModelInject constructor(
        val repository: Repository,
        @Assisted
        private val savedStateHandle: SavedStateHandle
) : QuestionBaseViewModel(repository, savedStateHandle){

}