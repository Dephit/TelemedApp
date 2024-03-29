package com.app.telemed.viewModels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LessonViewModel @ViewModelInject constructor(
    override val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : LessonBaseViewModel(repository, savedStateHandle){

    init {
        getEvent()
    }

    fun getEvent() {
        if(modelState.value !is ModelState.Loading) {
            modelState.value = ModelState.Loading
            viewModelScope.launch {
                repository.getEvent().collect {
                    postLesson(it)
                }
            }
        }
    }
}