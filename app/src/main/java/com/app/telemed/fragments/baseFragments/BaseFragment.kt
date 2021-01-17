package com.app.telemed.fragments.baseFragments

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.app.telemed.interfaces.BaseViewModelInterface
import com.app.telemed.viewModels.baseViewModels.ModelState
import com.app.telemed.interfaces.FragmentInterface


abstract class BaseFragment: Fragment(), FragmentInterface {

    abstract val viewModel: BaseViewModelInterface

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        restoreState(savedInstanceState)
        setListeners()
        observe()
    }

    override fun observe() {
        viewModel.getState().observe(viewLifecycleOwner, {
            if(it is ModelState.Loading)
                manageLoading(true)
            else
                manageLoading(false)
            if(it is ModelState.Error)
                manageError(true)
            else
                manageError(false)
            if(it is ModelState.Success)
                manageSuccess()
        })
    }

    open fun hideKeyboard() {
        val imm: InputMethodManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity?.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

