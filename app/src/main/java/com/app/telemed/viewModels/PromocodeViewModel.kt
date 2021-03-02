package com.app.telemed.viewModels

import android.os.Bundle
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.Comment
import com.app.telemed.viewModels.Promocode
import com.app.telemed.viewModels.baseViewModels.BaseViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState

class PromocodeViewModel @ViewModelInject constructor(
    val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel(repository, savedStateHandle) {

    var promocodes: List<Promocode>? = null

    fun restoreState(arguments: Bundle?) {
        promocodes = arguments?.getParcelableArrayList("promocodes")
        modelState.value = ModelState.Success(promocodes)
    }


}