package com.app.telemed.interfaces

import android.os.Bundle
import com.app.telemed.viewModels.baseViewModels.ModelState

interface FragmentInterface {
    fun observe()
    fun observeTo(modelState: ModelState)
    fun restoreState(savedInstanceState: Bundle?)
    fun setListeners()
    fun manageLoading(b: Boolean)
    fun<T> manageSuccess(obj: T? = null)
    fun manageError(bool: Boolean)
}