package com.app.telemed.fragments.baseFragments

import android.view.View
import com.app.telemed.viewModels.baseViewModels.EmailViewModelInterface
import com.app.telemed.setVisible

abstract class EmailFragment : BaseFragment() {

    abstract override val viewModel: EmailViewModelInterface

    abstract fun getEmail() : String

    abstract fun setEmail(email: String?)

    fun manageEmailFragment(editView: View, errorView: View){
        val bool = !viewModel.isValidEmail(getEmail()) && getEmail().isNotEmpty()
        errorView.setVisible(bool)
        editView.isActivated = bool
    }

}
