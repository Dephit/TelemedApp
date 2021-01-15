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

class PasswordRecoveryFragment : Fragment() {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Toast.makeText(context,  arguments?.getString("amount"), Toast.LENGTH_LONG).show()
    }

}