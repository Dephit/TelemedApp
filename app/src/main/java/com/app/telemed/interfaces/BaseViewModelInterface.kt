package com.app.telemed.interfaces

import androidx.lifecycle.MutableLiveData
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.flow.MutableStateFlow

interface BaseViewModelInterface {
    fun getState() : MutableStateFlow<ModelState>
}