package com.app.telemed.viewModels

import android.os.Bundle
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.Comment
import com.app.telemed.viewModels.baseViewModels.BaseViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState

class CommentListViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel(repository, savedStateHandle){

    var comments: List<Comment>? = null

    init {
        /*viewModelScope.launch {
            repository.getComments().collect {
                modelState.postValue(ModelState.Success(it))
            }
        }*/
    }

    fun restoreState(arguments: Bundle?) {
        comments = arguments?.getParcelableArrayList("comments")
        modelState.value = ModelState.Success(comments)
    }

}

