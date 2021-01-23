package com.app.telemed.viewModels

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.BaseViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel(repository, savedStateHandle){

    init {
        getComments()
    }



    private fun getComments() {
        viewModelScope.launch {
            repository.getComments().collect {
                if (it != null) {
                    doctorComments = it.subList(0, it.size / 2).toList()
                    instructorComments = it.subList(it.size / 2, it.size ).toList()
                    modelState.postValue(ModelState.Success(it))
                }
            }
        }
    }

    var doctorComments: List<Comment>? = null
    var instructorComments: List<Comment>? = null


    fun getDoctorComments(): Bundle = bundleOf("comments" to doctorComments )

    fun getInstructorComments(): Bundle = bundleOf("comments" to instructorComments )




}