package com.app.telemed

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.SavedStateHandle


open class EmailViewModel(
    repository: Repository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(repository, savedStateHandle) {

    val EMAIL = "AUTH_STATE"

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}
