package com.app.telemed.viewModels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.EmailViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PasswordRecoveryViewModel  @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : EmailViewModel(repository, savedStateHandle) {

    @ExperimentalCoroutinesApi
    fun restoreEmail(email: String) {
        if (!isValidEmail(email)) {
            modelState.value = ModelState.Error()
            return
        }

        viewModelScope.launch {
            repository.restoreEmail(email)
                .onStart { modelState.value = ModelState.Loading }
                .collect { value ->
                    if (value.status == "error")
                        modelState.value = ModelState.Error(value.message)
                    else
                        modelState.value = ModelState.Success(value)
                }
        }
    }
}