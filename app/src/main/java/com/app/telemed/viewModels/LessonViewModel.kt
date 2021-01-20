package com.app.telemed.viewModels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.telemed.LessonBaseViewModel
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.BaseViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LessonViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : LessonBaseViewModel(repository, savedStateHandle){

    init {
        getEvent()
    }

    fun getEvent() {
        modelState.postValue(ModelState.Loading)
        viewModelScope.launch {
            repository.getEvent().collect {
                if(it == null){
                    modelState.postValue(ModelState.Success(null))
                }else
                    modelState.postValue(ModelState.Success(it))

            }
        }
    }
}