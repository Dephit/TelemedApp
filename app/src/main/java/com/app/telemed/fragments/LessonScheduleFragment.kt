package com.app.telemed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.viewModels.LessonScheduleViewModel
import com.app.telemed.R
import com.app.telemed.databinding.LessonScheduleFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessonScheduleFragment : BaseFragment() {

    override val viewModel: LessonScheduleViewModel by navGraphViewModels(R.id.app_navigation) {
        defaultViewModelProviderFactory
    }

    lateinit var binding: LessonScheduleFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = LessonScheduleFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun observe() {

    }

    override fun restoreState(savedInstanceState: Bundle?) {

    }

    override fun setListeners() {
        with(binding){
            val badge = bottomNavigation
                .getOrCreateBadge(R.id.profile_menu)

            badge.isVisible = true
            bottomNavigation.selectedItemId = R.id.schedule_menu

            bottomNavigation.setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.lesson_menu -> fragment.findNavController().navigate(R.id.action_global_lessonFragment)
                    R.id.profile_menu -> fragment.findNavController().navigate(R.id.action_global_profileFragment)
                    R.id.schedule_menu -> fragment.findNavController().navigate(R.id.action_global_scheduleFragment)
                }
                //Toast.makeText(context, viewModel.str, Toast.LENGTH_SHORT).show()
                true
            }
        }
    }

    override fun manageLoading(b: Boolean) {

    }

    override fun manageSuccess() {

    }

    override fun manageError(bool: Boolean) {

    }

}