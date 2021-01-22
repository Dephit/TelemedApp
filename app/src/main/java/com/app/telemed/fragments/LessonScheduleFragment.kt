package com.app.telemed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.viewModels.LessonScheduleViewModel
import com.app.telemed.R
import com.app.telemed.databinding.LessonScheduleFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

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
            lifecycleScope.launch {
                withContext(Main){
                    delay(200)
                    fragment.findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
                        if(destination.id == R.id.lessonInfoFragment
                            || destination.id == R.id.lessonInProgressFragment
                            || destination.id == R.id.lessonQuesteningFragment
                                || destination.id == R.id.firstQuestionFragment
                                || destination.id == R.id.secondFragment
                                || destination.id == R.id.thirdQuestionFragment
                                || destination.id == R.id.endOfQuestioningFragment
                        ){
                            bottomNavigation.setVisible(false)
                        }else {
                            bottomNavigation.setVisible(true)
                        }

                        when (arguments?.getString("back")) {
                            "lesson" -> bottomNavigation.selectedItemId = R.id.lesson_menu
                            "schedule" -> bottomNavigation.selectedItemId = R.id.schedule_menu
                            "profile" -> bottomNavigation.selectedItemId = R.id.profile_menu
                            "auth"-> {
                                findNavController().popBackStack(R.id.authFragment, true)
                                findNavController().navigate(R.id.authFragment)
                            }
                        }
                    }
                }
            }

            bottomNavigation.selectedItemId = R.id.schedule_menu
            bottomNavigation.setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.lesson_menu -> fragment.findNavController().navigate(R.id.action_global_lessonFragment)
                    R.id.profile_menu -> fragment.findNavController().navigate(R.id.action_global_profileFragment)
                    R.id.schedule_menu -> fragment.findNavController().navigate(R.id.action_global_scheduleFragment)
                }
                true
            }
        }
    }

    override fun manageLoading(b: Boolean) {

    }

    override fun <T> manageSuccess(obj: T?) {

    }

    override fun manageError(bool: Boolean) {

    }

}