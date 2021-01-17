package com.app.telemed.viewModels.baseViewModels

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import com.app.telemed.interfaces.Repository
import com.app.telemed.interfaces.BaseViewModelInterface


open class EmailViewModel(
    repository: Repository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(repository, savedStateHandle), EmailViewModelInterface {

    val EMAIL = "AUTH_STATE"

    override fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

interface EmailViewModelInterface: BaseViewModelInterface {
    fun isValidEmail(target: CharSequence): Boolean
}
