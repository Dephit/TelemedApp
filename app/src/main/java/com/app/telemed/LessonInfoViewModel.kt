package com.app.telemed

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.telemed.interfaces.Lesson
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.BaseViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState

class LessonInfoViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel(repository, savedStateHandle){

    fun setLesson(lesson: Lesson?) {
        modelState.postValue(ModelState.Success(lesson))
    }

}