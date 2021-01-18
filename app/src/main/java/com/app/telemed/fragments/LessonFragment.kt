package com.app.telemed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.app.telemed.R
import com.app.telemed.databinding.LessonFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.viewModels.LessonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessonFragment : BaseFragment() {

    override val viewModel: LessonViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    lateinit var binding: LessonFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LessonFragmentBinding.inflate(inflater)
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

    override fun <T> manageSuccess(obj: T?) {

    }

    override fun manageError(bool: Boolean) {

    }

}