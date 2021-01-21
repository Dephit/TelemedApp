package com.app.telemed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.LessonInfoFragmentBinding
import com.app.telemed.interfaces.Lesson
import com.app.telemed.viewModels.baseViewModels.ModelState
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class LessonInfoFragment : LessonBaseFragment() {

    override val viewModel: LessonInfoViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    lateinit var binding: LessonInfoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = LessonInfoFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        viewModel.getState().value = ModelState.Normal
        viewModel.setLesson(arguments?.getParcelable(LESSONS))
        setTimer(binding.timerText)
    }

    override fun setListeners() {
        with(binding){
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            setBeginLessonButtoun(toBeginningOfLessonButton)
        }
    }

    override fun manageLoading(b: Boolean) {

    }

    @SuppressLint("SetTextI18n")
    override fun <T> manageSuccess(obj: T?) {
        (obj as Lesson).let {lesson->
            setTexts(binding, lesson)
            when {
                lesson.isPassed() -> setPassed(binding, lesson)
                lesson.isSoon() -> setSoon(binding, lesson)
                else -> setNotSoon(binding, lesson)
            }
            viewModel.setTimer(lesson)
        }
    }

    private fun setTexts(binding: LessonInfoFragmentBinding, lesson: Lesson) {
        with(binding){
            toolbar.title = lesson.title

            setDoctors(doctorAvatar, instructorAvatar, doctorText, instructorText)
            time.text = lesson.getTimeDetail()
        }
    }

    private fun setNotSoon(binding: LessonInfoFragmentBinding, lesson: Lesson) {
        with(binding){
            endingText.setVisible(false)
            doctorComent.setVisible(false)
            instructorComent.setVisible(false)
            pulseLayout.setVisible(false)
            soonWillBeginIcon.setVisible(false)

            if(!BuildConfig.IS_REHUB){
                toBeginningOfLessonButton.setText(R.string.make_appointment)
                setSignLessonButtoun(toBeginningOfLessonButton)
            }
            toBeginningOfLessonButton.setVisible(!BuildConfig.IS_REHUB)
        }
    }

    private fun setSoon(binding: LessonInfoFragmentBinding, lesson: Lesson) {
        with(binding){
            soonWillBeginIcon.setVisible(true)
            endingText.setVisible(true)
            toBeginningOfLessonButton.setVisible(true)
            doctorComent.setVisible(false)
            instructorComent.setVisible(false)
            pulseLayout.setVisible(false)

            endingText.text = getString(R.string.soon_will_start)
            toBeginningOfLessonButton.setText(R.string.to_beginning_of_lesson_text)
        }
    }

    private fun setPassed(binding: LessonInfoFragmentBinding, lesson: Lesson) {
        with(binding){
            doctorComent.setVisible(true)
            instructorComent.setVisible(true)
            pulseLayout.setVisible(true)
            toBeginningOfLessonButton.setVisible(false)
            timerText.setVisible(false)
            endingText.setVisible(true)
            soonWillBeginIcon.setVisible(false)

            yourPulseMin.text = Random.nextInt(220).toString()
            yourPulseMid.text = Random.nextInt(220).toString()
            yourPulseMax.text = Random.nextInt(220).toString()

            endingText.setTextColor(getColor(requireContext(), R.color.gray_text_color))
            endingText.text = getString(R.string.finished)
        }
    }

    override fun manageError(bool: Boolean) {

    }


}