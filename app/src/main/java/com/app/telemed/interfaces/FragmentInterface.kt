package com.app.telemed.interfaces

import android.os.Bundle

interface FragmentInterface {
    fun observe()
    fun restoreState(savedInstanceState: Bundle?)
    fun setListeners()
    fun manageLoading(b: Boolean)
    fun manageSuccess()
    fun manageError(bool: Boolean)
}