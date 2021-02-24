package com.app.telemed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.viewModels.PasswordRecoveryViewModel
import com.app.telemed.R
import com.app.telemed.databinding.PasswordRecoveryFragmentBinding
import com.app.telemed.enableProgress
import com.app.telemed.fragments.baseFragments.EmailFragment
import com.app.telemed.models.PasswordRestoreResponse
import com.app.telemed.viewModels.baseViewModels.ModelState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordRecoveryFragment : EmailFragment() {

    override val viewModel: PasswordRecoveryViewModel by navGraphViewModels(R.id.app_navigation) {
        defaultViewModelProviderFactory
    }

    lateinit var binding: PasswordRecoveryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PasswordRecoveryFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun observeTo(modelState: ModelState?) {
        if(modelState is ModelState.Error){
            modelState.msg?.let { showToast(it) }
        }
    }

    override fun <T> manageSuccess(obj: T?) {
        if(obj is PasswordRestoreResponse){
         //   showToast(obj.data)
        }
        viewModel.getState().value = ModelState.Normal
        findNavController().navigate(R.id.action_passwordRecoveryDest_to_passwordLinkSentFragment)
    }

    override fun setEmail(email: String?) {
        binding.emailEditText.setText(email)
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        savedInstanceState?.apply {
            setEmail(savedInstanceState.getString(viewModel.EMAIL))
        } ?: run {
            setEmail(arguments?.getString(viewModel.EMAIL))
        }
    }

    override fun setListeners() {
        with(binding){
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            emailEditText.addTextChangedListener { checkFieldsEmptiness(getEmail().isEmpty()) }

            restoreButton.setOnClickListener {
                hideKeyboard()
                viewModel.restoreEmail(getEmail())
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(viewModel.EMAIL, getEmail())
        super.onSaveInstanceState(outState)
    }

    override fun getEmail() = binding.emailEditText.text.toString().trim()

    private fun checkFieldsEmptiness(b: Boolean) { binding.restoreButton.isEnabled = !b }

    override fun manageLoading(b: Boolean) {
        with(binding){
            emailErrorText.isEnabled = !b
            checkFieldsEmptiness(b || getEmail().isEmpty())
            restoreButton.enableProgress(b, R.string.restore_text)
        }
    }

    override fun manageError(bool: Boolean) {
        with(binding){
            manageEmailFragment(emailEditText, emailErrorText)
        }
    }

}