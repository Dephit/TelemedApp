package com.app.telemed

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle

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


}