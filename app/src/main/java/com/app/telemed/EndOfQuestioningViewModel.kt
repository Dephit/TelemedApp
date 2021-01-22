package com.app.telemed

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.telemed.interfaces.Repository

class EndOfQuestioningViewModel  @ViewModelInject constructor(
        val repository: Repository,
        @Assisted
        private val savedStateHandle: SavedStateHandle
) : QuestionBaseViewModel(repository, savedStateHandle){
}