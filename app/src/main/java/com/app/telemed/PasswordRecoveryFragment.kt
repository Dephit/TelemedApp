package com.app.telemed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.PasswordRecoveryFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordRecoveryFragment : EmailFragment() {

    private val viewModel: PasswordRecoveryViewModel  by navGraphViewModels(R.id.app_navigation) {
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

    override fun observe() {
        viewModel.restoreState.observe(viewLifecycleOwner, {
            if(it is PasswordRecoveryState.Loading)
                manageLoading(true)
            else
                manageLoading(false)
            if(it is PasswordRecoveryState.Error)
                manageError(true)
            else
                manageError(false)
            if(it is PasswordRecoveryState.PasswordSent)
                manageSuccess()
        })
    }

    private fun manageSuccess() {
        viewModel.restoreState.value = PasswordRecoveryState.Normal
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
            emailErrorText.setVisible(bool)
        }
    }

}