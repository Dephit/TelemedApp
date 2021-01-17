package com.app.telemed.interfaces

import androidx.lifecycle.MutableLiveData
import com.app.telemed.viewModels.baseViewModels.ModelState

interface BaseViewModelInterface {
    fun getState() : MutableLiveData<ModelState>
}