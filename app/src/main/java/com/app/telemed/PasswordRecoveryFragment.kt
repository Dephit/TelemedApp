package com.app.telemed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.PasswordRecoveryFragmentBinding

class PasswordRecoveryFragment : BaseFragment() {

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

    }

    override fun restoreState(savedInstanceState: Bundle?) {

    }

    override fun setListeners() {

    }

    override fun manageLoading(b: Boolean) {
        TODO("Not yet implemented")
    }

    override fun manageError(bool: Boolean) {
        TODO("Not yet implemented")
    }

}