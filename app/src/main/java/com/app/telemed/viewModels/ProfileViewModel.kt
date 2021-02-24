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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.random.Random

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
                    modelState.value = ModelState.Success(it)
                }
            }
        }
    }

    var doctorComments: List<Comment>? = null
    var instructorComments: List<Comment>? = null


    fun getDoctorComments(): Bundle = bundleOf("comments" to doctorComments )

    fun getInstructorComments(): Bundle = bundleOf("comments" to instructorComments )

    fun getPromocodes(): Bundle {
        val list = mutableListOf<Promocode>()
        for (i in 0 .. 30){
            list.add(getPromocode(i))
        }
        return bundleOf("promocodes" to list)
    }



    private fun getPromocode(i: Int): Promocode {
        return Promocode(
            id = i,
            name = "$i",
            date = "до 12.10.2020 20:00",
            isPassed = Random.nextBoolean()
        )
    }

    @ExperimentalCoroutinesApi
    fun logOut() {
        viewModelScope.launch {
            if(modelState.value !is ModelState.Loading) {
                repository.logOut()
                        .onStart { modelState.value = ModelState.Loading }
                        .catch { modelState.value = ModelState.Error("Ошибка") }
                        .collect {
                            if (it != null) {
                                modelState.value = ModelState.Success(it)
                            }else {
                                modelState.value = ModelState.Error("Ошибка")
                            }
                        }
            }
        }
    }

}