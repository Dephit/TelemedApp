package com.app.telemed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.LessonFragmentBinding
import com.app.telemed.databinding.LessonInProgressFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessonInProgressFragment : BaseFragment() {

    override val viewModel: LessonInProgressViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    lateinit var binding: LessonInProgressFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return LessonInProgressFragmentBinding.inflate(inflater).run {
            binding = this
            root
        }
    }

    override fun restoreState(savedInstanceState: Bundle?) {

    }

    override fun setListeners() {
        with(binding){
            backButton.setOnClickListener { findNavController().popBackStack() }
        }

    }

    override fun manageLoading(b: Boolean) {

    }

    override fun <T> manageSuccess(obj: T?) {

    }

    override fun manageError(bool: Boolean) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}