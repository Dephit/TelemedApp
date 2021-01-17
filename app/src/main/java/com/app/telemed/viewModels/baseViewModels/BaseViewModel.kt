package com.app.telemed.viewModels.baseViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.telemed.interfaces.Repository
import com.app.telemed.interfaces.BaseViewModelInterface

sealed class ModelState {
    object Success : ModelState()
    class Error(msg: String? = null) : ModelState()
    object Loading : ModelState()
    object Normal : ModelState()
}

open class BaseViewModel constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
): ViewModel(), BaseViewModelInterface {

    protected val modelState: MutableLiveData<ModelState> = MutableLiveData(ModelState.Normal)

    override fun getState(): MutableLiveData<ModelState> = modelState
}

