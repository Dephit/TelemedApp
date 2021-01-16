package com.app.telemed

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Logged : AuthState()
    object Error : AuthState()
    object Loading : AuthState()
    object Normal : AuthState()
}

class AuthViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : EmailViewModel(repository, savedStateHandle) {

    val AUTH_STATE = "AUTH_STATE"
    val PASS = "AUTH_STATE"

    val authState: MutableLiveData<AuthState> =
            if(savedStateHandle.contains(AUTH_STATE))
                savedStateHandle.getLiveData(AUTH_STATE)
            else MutableLiveData(AuthState.Normal)

    fun getName() = repository.getClient()

    fun authorize(email: String, pass: String) {
        if(!isValidEmail(email)){
            authState.postValue(AuthState.Error)
            return
        }

        authState.postValue(AuthState.Loading)
        viewModelScope.launch {
            repository.logIn(email, pass)
                    .collect { value ->
                        if(value == 1){
                            authState.postValue(AuthState.Logged)
                        }else {
                            authState.postValue(AuthState.Error)
                        }
                    }
        }
    }

    private fun saveLoginInstance(email: String, pass: String) {
        savedStateHandle.set(AUTH_STATE, authState.value)
    }
}

