package com.app.telemed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.app.telemed.R
import com.app.telemed.databinding.LessonFragmentBinding
import com.app.telemed.getColor
import com.app.telemed.interfaces.Lesson
import com.app.telemed.setVisible
import com.app.telemed.viewModels.LessonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessonFragment : LessonBaseFragment() {

    override val viewModel: LessonViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    lateinit var binding: LessonFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LessonFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        setTimer(binding.timerText)
    }

    override fun setListeners() {
        setBeginLessonButton(binding.toBeginningOfLessonButton)
        binding.swiperefresh.setColorSchemeColors(getColor(requireContext(),R.color.primary_button_color))
        binding.swiperefresh.setOnRefreshListener { viewModel.getEvent() }
    }

    override fun manageLoading(b: Boolean) {
        binding.swiperefresh.isRefreshing = b
    }

    override fun <T> manageSuccess(obj: T?) {
        (obj as Lesson?)?.let {
            setLesson(it)
        } ?: run{
            setNoNextLesson()
        }
    }

    private fun setLesson(lesson: Lesson) {
        with(binding){
            soonWillStartLayout.setVisible(lesson.isSoon())
            coachLayout.setVisible(true)
            constraintLayout.setVisible(true)
            timerText.setVisible(!lesson.isPassed())
            toBeginningOfLessonButton.setVisible(lesson.isSoon())

            lessonName.text = lesson.title
            time.text = lesson.getTimeDetail()

            if(lesson.isPassed()){
                viewModel.setTimer()
            }else
                viewModel.setTimer(lesson)
            setDoctors(doctorAvatar, instructorAvatar, doctorText, instructorText)
        }
    }

    private fun setNoNextLesson() {
        with(binding){
            coachLayout.setVisible(false)
            constraintLayout.setVisible(false)
            soonWillStartLayout.setVisible(false)
            lessonName.text = getString(R.string.no_next_lesson)
        }
    }

    override fun manageError(bool: Boolean) {

    }

}