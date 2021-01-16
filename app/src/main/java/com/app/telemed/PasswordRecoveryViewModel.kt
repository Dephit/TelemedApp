package com.app.telemed

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

sealed class PasswordRecoveryState {
    object PasswordSent : PasswordRecoveryState()
    object Error : PasswordRecoveryState()
    object Loading : PasswordRecoveryState()
    object Normal : PasswordRecoveryState()
}

class PasswordRecoveryViewModel  @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : EmailViewModel(repository, savedStateHandle) {

    private val RESTORE_STATE: String = "RESTORE_STATE"

    val restoreState: MutableLiveData<PasswordRecoveryState> =
            if(savedStateHandle.contains(RESTORE_STATE))
                savedStateHandle.getLiveData(RESTORE_STATE)
            else MutableLiveData(PasswordRecoveryState.Normal)

    fun restoreEmail(email: String) {
        if(!isValidEmail(email)){
            restoreState.postValue(PasswordRecoveryState.Error)
            return
        }

        restoreState.postValue(PasswordRecoveryState.Loading)
        viewModelScope.launch {
            repository.restoreEmail(email)
                    .collect { value ->
                        if(value == 1){
                            restoreState.postValue(PasswordRecoveryState.PasswordSent)
                        }else {
                            restoreState.postValue(PasswordRecoveryState.Error)
                        }
                    }
        }
    }
}