package com.app.telemed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.AuthFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : BaseFragment() {

    private val viewModel: AuthViewModel by navGraphViewModels(R.id.app_navigation) {
        defaultViewModelProviderFactory
    }
    lateinit var binding: AuthFragmentBinding

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

    private fun setEmail(email: String?) {
        binding.emailEditText.setText(email)
    }

    private fun getEmail() = binding.emailEditText.text.toString().trim()

    private fun getPassword() = binding.passwordEditText.text.toString().trim()

    override fun observe() {
        viewModel.authState.observe(viewLifecycleOwner, {
            if(it is AuthState.Loading)
                manageLoading(true)
            else
                manageLoading(false)
            if(it is AuthState.Error)
                manageError(true)
            else
                manageError(false)
            if(it is AuthState.Logged)
                manageLogged()
            log(it.toString())
        })
    }

    private fun manageLogged() {
        val bundle = bundleOf("amount" to viewModel.getName())
        findNavController().navigate(R.id.toLessons, bundle)
    }

    override fun manageLoading(b: Boolean) {
        with(binding){
            emailErrorText.isEnabled = !b
            passwordEditText.isEnabled = !b
            checkFieldsEmptiness(b || isPassOrEmailEmpty())
            authorizeButton.enableProgress(b, R.string.authorize_text)
        }
    }

    override fun manageError(bool: Boolean) {
        with(binding){
            errorText.setVisible(bool)
            emailErrorText.setVisible(bool)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(viewModel.EMAIL, getEmail())
        outState.putString(viewModel.PASS, getPassword())
        super.onSaveInstanceState(outState)
    }

    override fun setListeners() {
        with(binding){
            passwordToggle.isActivated = passwordEditText.togglePasswordVisibility(false)
            passwordEditText.addTextChangedListener { checkFieldsEmptiness(isPassOrEmailEmpty()) }
            emailEditText.addTextChangedListener { checkFieldsEmptiness(isPassOrEmailEmpty()) }

            recoveryPasswordLink.setOnClickListener {
                val bundle = bundleOf("amount" to viewModel.getName())
                it.findNavController().navigate(R.id.action_authFragment_to_passwordRecoveryDest, bundle)
            }
            passwordToggle.setOnClickListener {
                passwordToggle.isActivated = passwordEditText.togglePasswordVisibility(!passwordToggle.isActivated)
            }
            authorizeButton.setOnClickListener {
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
