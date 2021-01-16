package com.app.telemed

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment(), FragmentInterface {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        restoreState(savedInstanceState)
        setListeners()
        observe()
    }

}

interface FragmentInterface {
    fun observe()
    fun restoreState(savedInstanceState: Bundle?)
    fun setListeners()
    fun manageLoading(b: Boolean)
    fun manageError(bool: Boolean)
}
