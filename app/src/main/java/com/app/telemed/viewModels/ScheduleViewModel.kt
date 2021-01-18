package com.app.telemed.viewModels

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.telemed.interfaces.Lesson
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.BaseViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ScheduleViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel(repository, savedStateHandle){

    init {
        getEvents()
    }

    var list: List<Lesson> = listOf()

    private fun getEvents() {
        modelState.postValue(ModelState.Loading)
        viewModelScope.launch {
            repository.getEvents()
                .collect { value ->
                    list = value
                    modelState.postValue(ModelState.Success(value))
                }
        }
    }
}