package com.app.telemed.fragments.baseFragments

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.app.telemed.interfaces.BaseViewModelInterface
import com.app.telemed.viewModels.baseViewModels.ModelState
import com.app.telemed.interfaces.FragmentInterface
import kotlinx.coroutines.flow.collect


abstract class BaseFragment: Fragment(), FragmentInterface {

    abstract val viewModel: BaseViewModelInterface

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        restoreState(savedInstanceState)
        setListeners()
        observe()
    }

    override fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.getState().collect{
                if(it is ModelState.Loading)
                    manageLoading(true)
                else
                    manageLoading(false)
                if(it is ModelState.Error)
                    manageError(true)
                else
                    manageError(false)
                if(it is ModelState.Success<*>){
                    manageSuccess(it.obj)
                }
                observeTo(it)
            }
        }
    }

    override fun observeTo(modelState: ModelState?) {

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

    fun showToast(msg: String?){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}

