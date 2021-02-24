package com.app.telemed.interfaces

import androidx.lifecycle.MutableLiveData
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

interface BaseViewModelInterface {
    @ExperimentalCoroutinesApi
    fun getState() : MutableStateFlow<ModelState>
}