package com.app.telemed

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.EmailViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

object USER {
    var token: String? = null
}

@ExperimentalCoroutinesApi
class InitSplashViewModel @ViewModelInject constructor(
private val repository: Repository,
@Assisted
private val savedStateHandle: SavedStateHandle
) : EmailViewModel(repository, savedStateHandle) {

    val PASS: String = "PASS"

    init {
        viewModelScope.launch {
            repository.isLogged().collect{
                USER.token = it?.token
                modelState.value = ModelState.Success(it)
            }
        }
    }
}