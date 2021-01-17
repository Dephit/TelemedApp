package com.app.telemed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.app.telemed.R
import com.app.telemed.viewModels.ScheduleViewModel
import com.app.telemed.databinding.ScheduleFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment : BaseFragment() {

    lateinit var binding: ScheduleFragmentBinding

    override val viewModel: ScheduleViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = ScheduleFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun observe() {
    }

    override fun restoreState(savedInstanceState: Bundle?) {
    }

    override fun setListeners() {
    }

    override fun manageLoading(b: Boolean) {

    }

    override fun manageSuccess() {

    }

    override fun manageError(bool: Boolean) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}