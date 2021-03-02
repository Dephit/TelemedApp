package com.app.telemed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.R
import com.app.telemed.databinding.InitSplashFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.models.LoginResponse
import com.app.telemed.viewModels.InitSplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitSplashFragment : BaseFragment() {

    override val viewModel: InitSplashViewModel by navGraphViewModels(R.id.app_navigation) {
        defaultViewModelProviderFactory
    }

    lateinit var binding: InitSplashFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InitSplashFragmentBinding.inflate(inflater,  null, false)
        return binding.root
    }

    override fun restoreState(savedInstanceState: Bundle?) {

    }

    override fun setListeners() {

    }

    override fun manageLoading(b: Boolean) {

    }

    override fun <T> manageSuccess(obj: T?) {
        if(obj is LoginResponse?){
            if(obj == null){
                findNavController().navigate(R.id.action_initSplashFragment_to_authFragment)
            }else {
                findNavController().navigate(R.id.toLessons)
            }
        }
    }

    override fun manageError(bool: Boolean) {

    }


}