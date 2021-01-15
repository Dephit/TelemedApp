package com.app.telemed

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.*
import com.app.telemed.databinding.AuthFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
    }

    private val viewModel: AuthViewModel by navGraphViewModels(R.id.app_navigation) {
        defaultViewModelProviderFactory
    }
    lateinit var binding: AuthFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AuthFragmentBinding.inflate(inflater,  null, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        with(binding){
            passwordToggle.isActivated = passwordEditText.togglePasswordVisibility(false)
            checkFieldsEmptiness(isPassOrEmailEmpty())

            passwordEditText.addTextChangedListener {
                checkFieldsEmptiness(isPassOrEmailEmpty())
            }

            emailEditText.addTextChangedListener {
                checkFieldsEmptiness(isPassOrEmailEmpty())
            }

            recoveryPasswordLink.setOnClickListener {
                val bundle = bundleOf("amount" to viewModel.getName())
                it.findNavController().navigate(R.id.action_authFragment_to_passwordRecoveryDest, bundle)
            }

            passwordToggle.setOnClickListener {
                passwordToggle.isActivated = passwordEditText.togglePasswordVisibility(!passwordToggle.isActivated)
            }

            authorizeButton.setOnClickListener {

            }
        }
    }

    private fun isPassOrEmailEmpty() =
        binding.passwordEditText.text.isNullOrEmpty() || binding.emailEditText.text.isNullOrBlank()


    private fun checkFieldsEmptiness(b: Boolean) {
        binding.authorizeButton.isEnabled = !b
    }

}

private fun EditText.togglePasswordVisibility(bool: Boolean): Boolean {
    transformationMethod = if(bool){
        null
    }else {
        PasswordTransformationMethod.getInstance()
    }
    return bool
}


