package com.app.telemed.viewModels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.EmailViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PasswordRecoveryViewModel  @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : EmailViewModel(repository, savedStateHandle) {

    fun restoreEmail(email: String) {
        if(!isValidEmail(email)){
            modelState.postValue(ModelState.Error())
            return
        }

        modelState.postValue(ModelState.Loading)
        viewModelScope.launch {
            repository.restoreEmail(email)
                    .collect { value ->
                        if(value == 1){
                            modelState.postValue(ModelState.Success(value))
                        }else {
                            modelState.postValue(ModelState.Error())
                        }
                    }
        }
    }
}