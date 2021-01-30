package com.app.telemed.viewModels.baseViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.telemed.interfaces.Repository
import com.app.telemed.interfaces.BaseViewModelInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class ModelState {
    class Success<T>(val obj: T? = null) : ModelState()
    class Error(msg: String? = null) : ModelState()
    object Loading : ModelState()
    object Normal : ModelState()
}

open class BaseViewModel constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
): ViewModel(), BaseViewModelInterface {

    protected val modelState: MutableStateFlow<ModelState> = MutableStateFlow(ModelState.Normal)

    override fun getState(): MutableStateFlow<ModelState> = modelState
}

