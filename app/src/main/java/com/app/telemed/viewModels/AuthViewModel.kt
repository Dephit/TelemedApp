package com.app.telemed.viewModels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.EmailViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AuthViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : EmailViewModel(repository, savedStateHandle) {

    val PASS: String = "PASS"

/*    init {
        viewModelScope.launch {
            repository.isLogged().collect{
                if(it != null){
                    modelState.value = ModelState.Success(it)
                }
            }
        }
    }*/

    @ExperimentalCoroutinesApi
    fun authorize(email: String, pass: String) {
        if(!isValidEmail(email)){
            modelState.value = ModelState.Error()
            return
        }
        viewModelScope.launch {
            repository.logIn(email, pass)
                .onStart { modelState.value = ModelState.Loading }
                .catch { modelState.value = ModelState.Error(it.message) }
                .collect { value -> modelState.value = ModelState.Success(value) }

        }
    }
}

