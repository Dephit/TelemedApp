package com.app.telemed.fragments

import com.app.telemed.models.LoginResponse
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.*
import com.app.telemed.databinding.AuthFragmentBinding
import com.app.telemed.fragments.baseFragments.EmailFragment
import com.app.telemed.viewModels.AuthViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : EmailFragment() {

    override val viewModel: AuthViewModel by navGraphViewModels(R.id.app_navigation) {
        defaultViewModelProviderFactory
    }
    lateinit var binding: AuthFragmentBinding

    override fun setEmail(email: String?) {
        binding.emailEditText.setText(email)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AuthFragmentBinding.inflate(inflater,  null, false)
        return binding.root
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        savedInstanceState?.apply {
            setEmail(savedInstanceState.getString(viewModel.EMAIL))
            setPassword(savedInstanceState.getString(viewModel.PASS))
        }
    }

    private fun setPassword(password: String?) {
        binding.passwordEditText.setText(password)
    }


    override fun getEmail() = binding.emailEditText.text.toString().trim()

    private fun getPassword() = binding.passwordEditText.text.toString().trim()

    override fun manageLoading(b: Boolean) {
        with(binding){
            emailErrorText.isEnabled = !b
            passwordEditText.isEnabled = !b
            checkFieldsEmptiness(b || isPassOrEmailEmpty())
            authorizeButton.enableProgress(b, R.string.authorize_text)
        }
    }

    override fun <T> manageSuccess(obj: T?) {
        viewModel.restoreState()
        val bundle = bundleOf(viewModel.EMAIL to getEmail())
        findNavController().navigate(R.id.toLessons, bundle)
    }

    override fun observeTo(modelState: ModelState?) {
        if(modelState is ModelState.Error){
            Toast.makeText(requireContext(), modelState.msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun manageError(bool: Boolean) {
        with(binding){
            errorText.setVisible(bool)
            passwordEditText.isActivated = bool
            manageEmailFragment(emailEditText, emailErrorText)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(viewModel.EMAIL, getEmail())
        outState.putString(viewModel.PASS, getPassword())
        super.onSaveInstanceState(outState)
    }

    override fun setListeners() {
        with(binding){
            emailErrorText.setVisible(false)
            passwordToggle.isActivated = passwordEditText.togglePasswordVisibility(false)
            passwordEditText.addTextChangedListener { checkFieldsEmptiness(isPassOrEmailEmpty()) }
            emailEditText.addTextChangedListener { checkFieldsEmptiness(isPassOrEmailEmpty()) }

            recoveryPasswordLink.setOnClickListener {
                val bundle = bundleOf(viewModel.EMAIL to getEmail())
                it.findNavController().navigate(R.id.action_authFragment_to_passwordRecoveryDest, bundle)
            }
            passwordToggle.setOnClickListener {
                passwordToggle.isActivated = passwordEditText.togglePasswordVisibility(!passwordToggle.isActivated)
            }
            authorizeButton.setOnClickListener {
                hideKeyboard()
                viewModel.authorize(getEmail(), getPassword())
            }
        }
    }

    private fun isPassOrEmailEmpty() = getEmail().isEmpty() || getPassword().isBlank()

    private fun checkFieldsEmptiness(b: Boolean) { binding.authorizeButton.isEnabled = !b }
}

private fun log(msg: String){
    Log.i("AUTH_FRAGMENT", msg)
}
